import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class MainMenu extends SuperScene {
    ArrayList<ItemMenu> itMenu;

    public MainMenu() {
        itMenu = new ArrayList<ItemMenu>();
        itMenu.add(new ItemMenu(Resource.startBut, Setting.CELL_SIZE * 15, Setting.CELL_SIZE * 8, Setting.CELL_SIZE * 8, Setting.CELL_SIZE * 2));
        itMenu.add(new ItemMenu(Resource.continueBut, Setting.CELL_SIZE * 15, Setting.CELL_SIZE * 11, Setting.CELL_SIZE * 8, Setting.CELL_SIZE * 2));
        itMenu.add(new ItemMenu(Resource.settingBut, Setting.CELL_SIZE * 15, Setting.CELL_SIZE * 14, Setting.CELL_SIZE * 8, Setting.CELL_SIZE * 2));
        itMenu.add(new ItemMenu(Resource.exitBut, Setting.CELL_SIZE * 15, Setting.CELL_SIZE * 17, Setting.CELL_SIZE * 8, Setting.CELL_SIZE * 2));
        itMenu.add(new ItemMenu(Resource.rec, Setting.CELL_SIZE * 2, Setting.CELL_SIZE * 16, Setting.CELL_SIZE * 3, Setting.CELL_SIZE * 3));
        itMenu.add(new ItemMenu(Resource.faq, Setting.CELL_SIZE * 22, Setting.CELL_SIZE, Setting.CELL_SIZE * 2, Setting.CELL_SIZE * 2));
    }

    public void update(long now) {
        focus(CreateGame.getFrame().getMousePosition());
        for (ItemMenu i : itMenu) {
            if (i.focus) {
                if (i.sizeUp < 1)
                    i.sizeUp += 0.005;
            } else {
                i.sizeUp = 0;
            }
        }
    }

    public void focus(Point p) {
        if (p != null) {
            for (ItemMenu menu : itMenu) {
                menu.focus = (menu.getX() <= p.getX() - 8) && ((menu.getX() + menu.getSizeX()) >= p.getX() - 8)
                        && (menu.getY() <= p.getY() - 25) && ((menu.getY() + menu.getSizeY()) >= p.getY() - 25);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(Resource.bGroundmm, 0, 0, CreateGame.getGame().getWidth(), CreateGame.getGame().getHeight(), null);
        for (ItemMenu i : itMenu) {
            g.drawImage(i.getIm(), i.getX(), i.getY(), i.getSizeX(), i.getSizeY(), null);
        }
    }

    public void click(int x, int y) {
        for (int i = 0; i < itMenu.size(); i++) {
            if ((itMenu.get(i).getX() <= x) && ((itMenu.get(i).getX() + itMenu.get(i).getSizeX()) >= x)
                    && (itMenu.get(i).getY() <= y) && ((itMenu.get(i).getY() + itMenu.get(i).getSizeY()) >= y)) {
                switch (i) {
                    case 0: {
                        CreateGame.createNewGame();
                        break;
                    }
                    case 1: {
                        CreateGame.setContinue();
                        break;
                    }
                    case 2: {
                        new SettDialog(CreateGame.getFrame());
                        break;
                    }
                    case 3: {
                        CreateGame.getGame().stop();
                        CreateGame.save();
                        Setting.save();
                        if (CreateGame.cont != null) {
                            CreateGame.cont.save();
                        }
                        System.exit(0);
                        break;
                    }
                    case 4: {
                        new Records(CreateGame.getFrame());
                        break;
                    }
                    case 5: {
                        CreateGame.getGame().setScene(new FAQScene());
                        break;
                    }
                }
            }
        }
    }
}

class ItemMenu {
    private final BufferedImage image;
    public double sizeUp;
    public boolean focus;
    private final int x;
    private final int y;
    private final int sizeX;
    private final int sizeY;

    public ItemMenu(BufferedImage image, int x, int y, int sizeX, int sizeY) {
        sizeUp = 0;
        this.image = image;
        this.x = x;
        this.y = y;
        this.focus = false;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public Image getIm() {
        return image;
    }

    public int getX() {
        return (int) (x - (Setting.CELL_SIZE / 2) * sizeUp);
    }

    public int getY() {
        return (int) (y - (Setting.CELL_SIZE / 2) * sizeUp);
    }

    public int getSizeX() {
        return (int) (sizeX + (Setting.CELL_SIZE * sizeUp));
    }

    public int getSizeY() {
        return (int) (sizeY + (Setting.CELL_SIZE * sizeUp));
    }

}
