import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Setting {
    static final int WORLD_WIDTH = 26;
    static final int WORLD_HEIGHT = 20;
    static int CELL_SIZE;
    static boolean playSound;
    static ArrayList<Integer> lsSize;
    static int keyUP;
    static int keyDOWN;
    static int keyLEFT;
    static int keyRIGHT;

    static String settingsFile = "Resource" + File.separator + "settings";

    public static void init() {
        CELL_SIZE = 30;
        playSound = true;
        keyUP = KeyEvent.VK_UP;
        keyDOWN = KeyEvent.VK_DOWN;
        keyLEFT = KeyEvent.VK_LEFT;
        keyRIGHT = KeyEvent.VK_RIGHT;
        lsSize = new ArrayList<>();
        lsSize.add(22);
        lsSize.add(30);
        lsSize.add(38);
    }

    public static void save() {
        File file = new File(settingsFile);
        try {
            if (!file.exists()) {
                boolean flag = file.createNewFile();
                if (!flag) {
                    throw new Exception();
                }
            }
            PrintStream out = new PrintStream(file);
            out.println(CELL_SIZE);
            out.println(playSound);
            out.println(keyUP);
            out.println(keyDOWN);
            out.println(keyLEFT);
            out.println(keyRIGHT);
            out.close();
        } catch (Exception ignored) {
        }
    }

    public static void load() throws Exception {
        File file = new File(settingsFile);
        if (file.exists()) {
            Scanner cin = new Scanner(file);
            CELL_SIZE = Integer.parseInt(cin.nextLine().replaceAll(" ", ""));
            playSound = Boolean.parseBoolean(cin.nextLine().replaceAll(" ", ""));
            keyUP = Integer.parseInt(cin.nextLine().replaceAll(" ", ""));
            keyDOWN = Integer.parseInt(cin.nextLine().replaceAll(" ", ""));
            keyLEFT = Integer.parseInt(cin.nextLine().replaceAll(" ", ""));
            keyRIGHT = Integer.parseInt(cin.nextLine().replaceAll(" ", ""));
            cin.close();
        }
    }

}
