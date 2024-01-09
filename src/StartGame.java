public class StartGame {
    public static void main(String[] args) {
        try {
            new Resource();
        } catch (Exception ex) {
            System.exit(0);
        }
        try {
            Setting.init();
            Setting.load();
            Continue.load();
            CreateGame.load();
        } catch (Exception ex) {
            Setting.init();
        }
        CreateGame.initLvl();
        CreateGame.createNewFrame();
    }
}
