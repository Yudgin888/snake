import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;


public class CanvasGame extends Canvas implements Runnable {
    private Thread gameThread;
    private AtomicBoolean running;
    private Input input;
    private SuperScene scene;
    public boolean update;



    public CanvasGame(Dimension size){
        running = new AtomicBoolean(false);
        update = true;
        setSize(size);
        input = new Input();
        addKeyListener(input);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent event) {
                if(scene instanceof Scene){
                    CreateGame.getGame().sceneStart();
                }
            }
            @Override
            public void focusLost(FocusEvent event) {
                if(scene instanceof Scene){
                    CreateGame.getGame().scenePause();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scene.click(e.getX(), e.getY());
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if((scene instanceof Scene) && (e.getKeyCode() == 80 || e.getKeyCode() == 32) && update){
                    CreateGame.getGame().scenePause();
                    return;
                }
                if(!update){
                    CreateGame.getGame().sceneStart();
                    input.getKeyEvents();
                }
            }
        });
    }

    public void start(){
        if(running.compareAndSet(false, true)){
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void stop(){
        if(running.compareAndSet(true, false)){
            try{
                gameThread.join();
            }catch (InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
    }

    public void scenePause() {
        update = false;
    }

    public void sceneStart(){
        if(scene instanceof Scene) {
            Scene sc = (Scene) scene;
            if (sc.getFruit() != null) {
                sc.getFruit().lastUpdate = System.nanoTime();
            }
            sc.lastUpdate = System.nanoTime();
        }
        update = true;
    }

    public Dimension getScreenSize(){
        return this.getSize();
    }

    public Input getInput(){
        return this.input;
    }

    public void setScene(SuperScene scene){
        this.scene = scene;
    }

    public void run(){
        while(running.get()){
            if(scene == null){
                continue;
            }
            if(update) {
                long now = System.nanoTime();
                scene.update(now);
            }
            Graphics2D g2d = (Graphics2D)getBufferStrategy().getDrawGraphics();
            scene.draw(g2d);
            getBufferStrategy().show();
        }
    }

}
