import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;


public class CreateGame {
    private static CanvasGame game;
    private static JFrame frame;
    private static ArrayList<ArrayList<Stone>> levels;
    private static int currLvl;
    public static Continue cont;
    public static int kolH;
    public static int kolB;
    public static int score;
    public static double time;
    public static ArrayList<String[]> lsScore;
    public static Sound music;


    public static void createNewFrame(){
        if(frame != null){
            game.stop();
            frame.dispose();
        }
        Dimension screenSize = new Dimension(Setting.WORLD_WIDTH * Setting.CELL_SIZE, Setting.WORLD_HEIGHT * Setting.CELL_SIZE);
        frame = new JFrame("Snake");
        frame.setIconImage(Resource.icon);
        frame.setFocusable(false);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        game = new CanvasGame(screenSize);
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        game.createBufferStrategy(2);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                if(cont != null) {
                    cont.save();
                }
                game.stop();
                CreateGame.stopMusic();
                Setting.save();
                CreateGame.save();
            }
        });
        frame.requestFocus();
        game.requestFocus();
        game.setScene(new MainMenu());
        playMusic(1);
        game.start();
    }

    public static void createNewGame(){
        setCurrLvl(-1);
        kolH = 5;
        kolB = 0;
        score = 0;
        time = 0;
        setNextScene();
        playMusic(2);
    }

    public static void setContinue(){
        if(cont != null) {
            Scene scene = cont.getScene();
            scene.lastUpdate = scene.getSnake().lastUpdate = System.nanoTime();
            if(scene.getFruit() != null){
                scene.getFruit().lastUpdate = System.nanoTime();
            }
            playMusic(2);
            game.setScene(scene);
            game.scenePause();
            currLvl = cont.getCurrLvl();
            kolH = cont.getKolH();
            kolB = cont.getKolB();
            score = cont.getScore();
            time = cont.getTime();
        }else{
            createNewGame();
        }
    }

    public static void setNextScene(){
        currLvl++;
        kolB = 0;
        game.setScene(new Scene());
        game.scenePause();
    }

    public static void playMusic(int num){
        stopMusic();
        if(Setting.playSound) {
            switch (num) {
                case 1: {
                    if(Resource.s_mainTheme != null) {
                        music = new Sound(Resource.s_mainTheme, true);
                        music.startThread();
                    }
                    break;
                }
                case 2: {
                    if(Resource.playList != null) {
                        music = new Sound(Resource.playList, true);
                        music.startThread();
                    }
                    break;
                }
                case 3: {
                    if(Resource.s_finish != null) {
                        music = new Sound(Resource.s_finish, true);
                        music.startThread();
                    }
                    break;
                }
                case 4: {
                    if(Resource.s_gameOv != null) {
                        music = new Sound(Resource.s_gameOv, true);
                        music.startThread();
                    }
                    break;
                }
            }
        }
    }

    public static void stopMusic(){
        if(music != null){
            music.stopThread();
        }
    }

    public static void sortScore(){
        for(int i = 0; i < lsScore.size(); i++){
            for(int j = i+1; j < lsScore.size(); j++){
                if(Integer.parseInt(lsScore.get(i)[1]) < Integer.parseInt(lsScore.get(j)[1])){
                    lsScore.add(i, lsScore.get(j));
                    lsScore.remove(j+1);
                    if(j > 0) {
                        j--;
                    }
                    if(i > 0) {
                        i--;
                    }
                }
            }
        }
    }
    
    public static void initLvl(){
        levels = new ArrayList<ArrayList<Stone>>();
        ArrayList<Stone> lvl6 = new ArrayList<Stone>();
        int []arr6 = {17, 18, 18, 18};
        for(int i = 0; i < arr6.length; i++) {
            Stone s = new Stone(arr6[i], arr6[i+1]);
            lvl6.add(s);
            i++;
        }
        levels.add(lvl6);
        ArrayList<Stone> lvl0 = new ArrayList<Stone>();
        int []arr0 = {5, 4, 1, 12, 10, 14, 19, 14, 22, 6, 3, 17, 14, 8, 17, 18, 18, 18};
        for(int i = 0; i < arr0.length; i++) {
            Stone s = new Stone(arr0[i], arr0[i+1]);
            lvl0.add(s);
            i++;
        }
        levels.add(lvl0);
        ArrayList<Stone> lvl1 = new ArrayList<Stone>();
        int []arr1 = {2, 3, 3, 3, 4, 3, 23, 3, 24, 3, 25, 3, 2, 4, 25, 4, 2, 5, 25, 5, 2, 15, 25, 15, 2, 16, 25, 16,
                2, 17, 3, 17, 4, 17, 23, 17, 24, 17, 25, 17, 17, 18, 18, 18};
        for(int i = 0; i < arr1.length; i++) {
            Stone s = new Stone(arr1[i], arr1[i+1]);
            lvl1.add(s);
            i++;
        }
        levels.add(lvl1);
        ArrayList<Stone> lvl2 = new ArrayList<Stone>();
        int []arr2 = {7, 4, 20, 4, 7, 5, 20, 5, 7, 6, 20, 6, 7, 7, 20, 7, 12, 8, 13, 8, 14, 8, 15, 8, 12, 9, 13, 9, 14, 9, 15, 9,
                12, 10, 13, 10, 14, 10, 15, 10, 12, 11, 13, 11, 14, 11, 15, 11, 7, 12, 20, 12, 7, 13, 20, 13, 7, 14, 20, 14,
                7, 15, 20, 15, 17, 18, 18, 18};
        for(int i = 0; i < arr2.length; i++) {
            Stone s = new Stone(arr2[i], arr2[i+1]);
            lvl2.add(s);
            i++;
        }
        levels.add(lvl2);
        ArrayList<Stone> lvl3 = new ArrayList<Stone>();
        int []arr3 = {7, 3, 8, 3, 9, 3, 10, 3, 11, 3, 12, 3, 13, 3, 14, 3, 15, 3, 16, 3, 17, 3, 18, 3, 19, 3, 20, 3,
                3, 4, 3, 5, 3, 6, 3, 7, 3, 8, 3, 9, 3, 10, 3, 11, 3, 12, 3, 13, 3, 14, 3, 15,
                24, 4, 24, 5, 24, 6, 24, 7, 24, 8, 24, 9, 24, 10, 24, 11, 24, 12, 24, 13, 24, 14, 24, 15,
                7, 16, 8, 16, 9, 16, 10, 16, 11, 16, 12, 16, 13, 16, 14, 16, 15, 16, 16, 16, 17, 16, 18, 16, 19, 16, 20, 16, 17, 18, 18, 18};
        for(int i = 0; i < arr3.length; i++) {
            Stone s = new Stone(arr3[i], arr3[i+1]);
            lvl3.add(s);
            i++;
        }
        levels.add(lvl3);
        ArrayList<Stone> lvl4 = new ArrayList<Stone>();
        int []arr4 = {3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 19, 8, 20, 7, 21, 6, 22, 5, 23, 4, 24, 3, 3, 16, 4, 15, 5, 14, 6, 13, 7, 12, 8, 11,
                19, 11, 20, 12, 21, 13, 22, 14, 23, 15, 24, 16, 17, 18, 18, 18};
        for(int i = 0; i < arr4.length; i++) {
            Stone s = new Stone(arr4[i], arr4[i+1]);
            lvl4.add(s);
            i++;
        }
        levels.add(lvl4);
        ArrayList<Stone> lvl5 = new ArrayList<Stone>();
        int []arr5 = new int[168];
        int k = 1, i = 0;
        for(; i < 52; i++){
            arr5[i] = k;
            k++;
            i++;
            arr5[i] = 1;
        }
        k = 2;
        for(; i < 86; i++){
            arr5[i] = 1;
            i++;
            arr5[i] = k;
            k++;
        }
        k = 2;
        for(; i < 120; i++){
            arr5[i] = 26;
            i++;
            arr5[i] = k;
            k++;
        }
        k = 2;
        for(; i < 168; i++) {
            arr5[i] = k;
            k++;
            i++;
            arr5[i] = 18;
        }
        i = 0;
        for(; i < 168; i++) {
            Stone s = new Stone(arr5[i], arr5[i+1]);
            lvl5.add(s);
            i++;
        }
        levels.add(lvl5);
    }

    public static CanvasGame getGame(){
        return game;
    }

    public static JFrame getFrame(){ return frame; }

    public static ArrayList<Stone> getListLvl(){
        ArrayList<Stone> list = new ArrayList<Stone>();
        for(int i = 0; i < levels.get(currLvl).size(); i++){
            list.add(new Stone(levels.get(currLvl).get(i)));
        }
        return list;
    }

    public static int getKolLvl(){
        return levels.size();
    }

    public static void setCurrLvl(int lvl){
        currLvl = lvl;
    }

    public static int getCurrLvl(){
        return currLvl;
    }

    public static void load(){
        lsScore = new ArrayList<String[]>();
        File fl = new File("Resource\\score");
        if(fl.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("Resource\\score"));
                lsScore = (ArrayList<String[]>) in.readObject();
                in.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static void save(){
        File fl = new File("Resource\\score");
        try {
            if(!fl.exists()){
                fl.createNewFile();
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Resource\\score"));
            out.writeObject(lsScore);
            out.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}

class Continue implements Serializable{
    private Scene scene;
    private int currLvl;
    private int kolH;
    private int kolB;
    private int score;
    private double time;

    Continue(Scene scene, int currLvl, int kolH, int kolB, int score, double time){
        this.scene = scene;
        this.currLvl = currLvl;
        this.kolH = kolH;
        this.kolB = kolB;
        this.score = score;
        this.time = time;
    }

    public void save(){
        File fl = new File("Resource\\save");
        try {
            if(!fl.exists()){
                fl.createNewFile();
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Resource\\save"));
            out.writeObject(this);
            out.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void load(){
        File fl = new File("Resource\\save");
        if(fl.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("Resource\\save"));
                CreateGame.cont = (Continue) in.readObject();
                in.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else{
            CreateGame.cont = null;
        }
    }

    public Scene getScene(){
        return scene;
    }

    public int getCurrLvl(){
        return currLvl;
    }

    public int getKolH(){
        return kolH;
    }

    public int getKolB(){
        return kolB;
    }

    public int getScore(){
        return score;
    }

    public double getTime(){
        return time;
    }
}