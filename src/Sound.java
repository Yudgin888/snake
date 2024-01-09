
import java.io.File;
import javax.sound.sampled.*;


public class Sound implements Runnable {
    private boolean running;
    private boolean released;
    private Clip clip;
    private FloatControl volumeC;
    private boolean playing;
    private int position;
    private int number;
    private boolean method;
    private boolean loop;
    private final String[] playLs;


    public Sound(String s, boolean loop) {
        playLs = new String[1];
        playLs[0] = s;
        number = 0;
        init(loop);
    }

    public Sound(String[] s, boolean loop) {
        playLs = s;
        if (CreateGame.getCurrLvl() >= 0 && CreateGame.getCurrLvl() <= Resource.playList.length) {
            number = CreateGame.getCurrLvl();
        } else {
            number = 0;
        }
        init(loop);
    }

    private void init(boolean loop) {
        running = true;
        released = false;
        clip = null;
        volumeC = null;
        playing = true;
        method = false;
        position = 0;
        this.loop = loop;
    }

    public void startThread() {
        Thread soundThread = new Thread(this);
        soundThread.start();
    }

    public void stopThread() {
        running = false;
        playing = false;
        stop();
        if (clip != null) {
            clip.close();
        }
    }

    public void run() {
        while (running) {
            playing = true;
            while (playing) {
                if (number > playLs.length - 1) {
                    number = 0;
                }
                load(playLs[number]);
                play();
                join();
            }
            number++;
            if (!loop) {
                stopThread();
            }
        }
    }

    private void load(String s) {
        try {
            File f = new File(s);
            AudioInputStream stream = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            volumeC = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            released = true;
            setVolume((float) 0.8);
            stream.close();
        } catch (Exception exc) {
            released = false;
        }
    }

    public void play() {
        if (released) {
            clip.setFramePosition(position);
            clip.start();
        }
    }

    public void pause() {
        if (playing && clip != null) {
            clip.stop();
            method = true;
            position = clip.getFramePosition();
        }
    }

    public void stop() {
        if (playing && clip != null) {
            clip.stop();
            clip.flush();
            method = true;
            position = 0;
        }
    }

    public void next() {
        if (playLs.length > 1) {
            clip.stop();
        }
    }

    public void setVolume(float x) {
        if (x < 0) x = 0;
        if (x > 1) x = 1;
        float min = volumeC.getMinimum();
        float max = volumeC.getMaximum();
        volumeC.setValue((max - min) * x + min);
    }

    public float getVolume() {
        float v = volumeC.getValue();
        float min = volumeC.getMinimum();
        float max = volumeC.getMaximum();
        return (v - min) / (max - min);
    }

    public void setPosition(int pos) {
        position = pos;
    }

    public int getPosition() {
        return position;
    }

    private void join() {
        if (!released) {
            return;
        }
        synchronized (clip) {
            try {
                while (playing) {
                    clip.wait();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static void play1Sound(String s) {
        Sound snd = new Sound(s, false);
        snd.startThread();
    }

    private class Listener implements LineListener {
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                synchronized (clip) {
                    clip.notify();
                    if (!method) {
                        playing = false;
                    }
                    method = false;
                }
            }
        }
    }

}
