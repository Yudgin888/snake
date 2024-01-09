import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Yudgin on 08.12.2014.
 */
public class FAQScene extends SuperScene {

    public void update(long nanosPassed) {
        for (KeyEvent event : CreateGame.getGame().getInput().getKeyEvents()) {
            if (event.getKeyCode() == KeyEvent.VK_ENTER || event.getKeyCode() == 27) {
                CreateGame.getGame().setScene(new MainMenu());
            }
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(Resource.bGroundFaq, 0, 0, CreateGame.getGame().getWidth(), CreateGame.getGame().getHeight(), null);
        g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE));
        g.drawImage(Resource.fruit.get(0), Setting.CELL_SIZE * 6, Setting.CELL_SIZE, Setting.CELL_SIZE * 2, Setting.CELL_SIZE * 2, null);
        g.drawString(" = + ", Setting.CELL_SIZE * 8, Setting.CELL_SIZE * 2 + Setting.CELL_SIZE / 2);
        g.drawImage(Resource.body, Setting.CELL_SIZE * 10, Setting.CELL_SIZE, Setting.CELL_SIZE * 2, Setting.CELL_SIZE * 2, null);

        g.drawImage(Resource.fruit.get(1), Setting.CELL_SIZE * 6, Setting.CELL_SIZE * 3 + Setting.CELL_SIZE / 2, Setting.CELL_SIZE * 2, Setting.CELL_SIZE * 2, null);
        g.drawString(" = + ", Setting.CELL_SIZE * 8, Setting.CELL_SIZE * 5);
        g.drawImage(Resource.body, Setting.CELL_SIZE * 10, Setting.CELL_SIZE * 3 + Setting.CELL_SIZE / 2, Setting.CELL_SIZE * 2, Setting.CELL_SIZE * 2, null);
        g.drawImage(Resource.body, Setting.CELL_SIZE * 12, Setting.CELL_SIZE * 3 + Setting.CELL_SIZE / 2, Setting.CELL_SIZE * 2, Setting.CELL_SIZE * 2, null);

        g.drawImage(Resource.fruit.get(2), Setting.CELL_SIZE * 16, Setting.CELL_SIZE, Setting.CELL_SIZE * 2, Setting.CELL_SIZE * 2, null);
        g.drawString(" = + ", Setting.CELL_SIZE * 18, Setting.CELL_SIZE * 2 + Setting.CELL_SIZE / 2);
        g.drawImage(Resource.speed, Setting.CELL_SIZE * 20, Setting.CELL_SIZE, Setting.CELL_SIZE * 2 - Setting.CELL_SIZE / 2, Setting.CELL_SIZE * 2 - Setting.CELL_SIZE / 2, null);

        g.drawImage(Resource.stone, Setting.CELL_SIZE * 16, Setting.CELL_SIZE * 3 + Setting.CELL_SIZE / 2, Setting.CELL_SIZE * 2, Setting.CELL_SIZE * 2, null);
        g.drawString(" = - ", Setting.CELL_SIZE * 18, Setting.CELL_SIZE * 5);
        g.drawImage(Resource.body, Setting.CELL_SIZE * 20, Setting.CELL_SIZE * 3 + Setting.CELL_SIZE / 2, Setting.CELL_SIZE * 2, Setting.CELL_SIZE * 2, null);

        g.drawString(" -  P", Setting.CELL_SIZE * 22 - Setting.CELL_SIZE / 2, Setting.CELL_SIZE * 9);

        g.drawString(KeyEvent.getKeyText(Setting.keyUP), Setting.CELL_SIZE * 16 - Setting.CELL_SIZE / 6, Setting.CELL_SIZE * 9);
        g.drawString(KeyEvent.getKeyText(Setting.keyRIGHT), Setting.CELL_SIZE * 20 + Setting.CELL_SIZE / 3, Setting.CELL_SIZE * 14 - Setting.CELL_SIZE / 6);
        if (KeyEvent.getKeyText(Setting.keyDOWN).compareTo("Down") == 0) {
            g.drawString(KeyEvent.getKeyText(Setting.keyDOWN), Setting.CELL_SIZE * 15 - Setting.CELL_SIZE / 6, Setting.CELL_SIZE * 19);
        } else {
            g.drawString(KeyEvent.getKeyText(Setting.keyDOWN), Setting.CELL_SIZE * 16 - Setting.CELL_SIZE / 6, Setting.CELL_SIZE * 19);
        }
        if (KeyEvent.getKeyText(Setting.keyLEFT).compareTo("Left") == 0) {
            g.drawString(KeyEvent.getKeyText(Setting.keyLEFT), Setting.CELL_SIZE * 10 + Setting.CELL_SIZE / 3, Setting.CELL_SIZE * 14 - Setting.CELL_SIZE / 6);
        } else {
            g.drawString(KeyEvent.getKeyText(Setting.keyLEFT), Setting.CELL_SIZE * 11 + Setting.CELL_SIZE / 3, Setting.CELL_SIZE * 14 - Setting.CELL_SIZE / 6);
        }

    }

    public void click(int x, int y) {
    }
}
