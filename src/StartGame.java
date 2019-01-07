import java.util.Scanner;

public class StartGame {
    public static void main(String[] args) {
        try {
            new Resource();
        } catch (Exception ex) {
            System.exit(0);
        }
        try{
            new Setting();
            Setting.load();
            Continue.load();
            CreateGame.load();
        }catch (Exception ex){
            new Setting();
        }
        CreateGame.initLvl();
        CreateGame.createNewFrame();
        Scanner cin;
        try {
            while (CreateGame.getFrame() != null) {
                cin = new Scanner(System.in);
                String str = cin.nextLine();
                if (str.compareTo("endlvl") == 0) {
                    CreateGame.kolB = CreateGame.getCurrLvl()*10 + 29;
                }
            }
        }catch (Exception ex){
        }
    }
}
