import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Resource {
    public static BufferedImage icon;
    public static BufferedImage iconSett;
    public static BufferedImage head0;
    public static BufferedImage head180;
    public static BufferedImage headA0;
    public static BufferedImage headA180;
    public static BufferedImage body;
    public static BufferedImage body2;
    public static BufferedImage bGround1;
    public static BufferedImage bGroundmm;
    public static BufferedImage bGroundset;
    public static BufferedImage bFin;
    public static BufferedImage bGroundOv;
    public static BufferedImage briefing;
    public static BufferedImage board;
    public static BufferedImage gameOver;
    public static BufferedImage startBut;
    public static BufferedImage continueBut;
    public static BufferedImage settingBut;
    public static BufferedImage rec;
    public static BufferedImage faq;
    public static BufferedImage bGroundFaq;
    public static BufferedImage speed;
    public static BufferedImage exitBut;
    public static BufferedImage pause;
    public static ArrayList<BufferedImage> fruit;
    public static BufferedImage stone;
    public static BufferedImage ouch;
    public static BufferedImage exit;
    public static BufferedImage h;
    public static BufferedImage cl;
    public static String s_mainTheme;
    public static String s_finish;
    public static String s_gameOv;
    public static String s_p_1;
    public static String s_p_2;
    public static String s_ston;
    public static String[] playList = null;

    static String imagesPath = "Resource" + File.separator + "images";

    static String soundPath = "Resource" + File.separator + "sounds";


    Resource() throws Exception {
        fruit = new ArrayList<>();
        load();
    }

    public void load() throws Exception {
        File file = new File(imagesPath + File.separator + "icon.png");
        icon = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "tool.png");
        iconSett = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "h.png");
        h = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "ouch.png");
        ouch = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "exit.png");
        exit = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "speed.png");
        speed = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "cl.png");
        cl = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "pause.jpg");
        pause = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "bGround_lvl1.jpg");
        bGround1 = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "bGroundFaq.jpg");
        bGroundFaq = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "bGround_mm.jpg");
        bGroundmm = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "bGroundOv.jpg");
        bGroundOv = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "bGround_set.jpg");
        bGroundset = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "bFin.jpg");
        bFin = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "briefing.jpg");
        briefing = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "game_over.jpg");
        gameOver = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "board.jpg");
        board = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "head0.png");
        head0 = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "head180.png");
        head180 = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "headA0.png");
        headA0 = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "headA180.png");
        headA180 = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "body.png");
        body = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "body2.png");
        body2 = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "rec.png");
        rec = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "faq.png");
        faq = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "apple.png");
        fruit.add(ImageIO.read(file));
        file = new File(imagesPath + File.separator + "two_apples.png");
        fruit.add(ImageIO.read(file));
        file = new File(imagesPath + File.separator + "banana.png");
        fruit.add(ImageIO.read(file));
        file = new File(imagesPath + File.separator + "stone.png");
        stone = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "startButton.png");
        startBut = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "contButton.png");
        continueBut = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "settingButton.png");
        settingBut = ImageIO.read(file);
        file = new File(imagesPath + File.separator + "exitButton.png");
        exitBut = ImageIO.read(file);

        file = new File(soundPath + File.separator + "~MainTheme.wav");
        if (file.exists()) {
            s_mainTheme = soundPath + File.separator + "~MainTheme.wav";
        } else {
            s_mainTheme = null;
        }
        file = new File(soundPath + File.separator + "~finish.wav");
        if (file.exists()) {
            s_finish = soundPath + File.separator + "~finish.wav";
        } else {
            s_finish = null;
        }
        file = new File(soundPath + File.separator + "~game_over.wav");
        if (file.exists()) {
            s_gameOv = soundPath + File.separator + "~game_over.wav";
        } else {
            s_gameOv = null;
        }
        file = new File(soundPath + File.separator + "~potion_1.wav");
        if (file.exists()) {
            s_p_1 = soundPath + File.separator + "~potion_1.wav";
        } else {
            s_p_1 = null;
        }
        file = new File(soundPath + File.separator + "~potion_2.wav");
        if (file.exists()) {
            s_p_2 = soundPath + File.separator + "~potion_2.wav";
        } else {
            s_p_2 = null;
        }
        file = new File(soundPath + File.separator + "~ston.wav");
        if (file.exists()) {
            s_ston = soundPath + File.separator + "~ston.wav";
        } else {
            s_ston = null;
        }

        file = new File(soundPath);
        if (file.exists()) {
            String[] str = file.list();
            assert str != null;
            if (str.length != 0) {
                ArrayList<String> ls = new ArrayList<>();
                for (String s : str) {
                    String[] tmp = s.split("[.]");
                    if (s.charAt(0) != '~' && tmp[tmp.length - 1].compareTo("wav") == 0) {
                        ls.add(soundPath + File.separator + s);
                    }
                }
                if (ls.size() != 0) {
                    playList = new String[ls.size()];
                    for (int i = 0; i < ls.size(); i++) {
                        playList[i] = ls.get(i);
                    }
                }
            }
        }
    }
}
