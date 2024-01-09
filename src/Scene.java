import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;


public class Scene extends SuperScene implements Serializable {
    private Snake snake;
    private Fruit fruit;
    private double time;
    public long lastUpdate;
    private boolean end;
    private boolean setEnd;
    private boolean ouch;
    private int brief;
    private final ArrayList<Stone> lsStone;

    public Scene() {
        snake = new Snake(2, 2, Direction.RIGHT, 3 + CreateGame.getCurrLvl());
        lastUpdate = snake.lastUpdate = System.nanoTime();
        time = 0.0;
        end = false;
        setEnd = false;
        ouch = false;
        brief = 0;
        lsStone = CreateGame.getListLvl();
        placeFruit();
    }

    @Override
    public void update(long now) {
        double val = (now - lastUpdate) / 1000000;
        val /= 1000;
        time += val;
        lastUpdate = now;

        if (isEndScene()) {
            CreateGame.cont = new Continue(this, CreateGame.getCurrLvl(), CreateGame.kolH, CreateGame.kolB, CreateGame.score, CreateGame.time);
            return;
        }
        if (isGameOver()) {
            CreateGame.playMusic(4);
            CreateGame.getGame().setScene(new OverScene(3, calcScore(), time));
            return;
        }
        processInput();
        if (ouch && now > snake.lastUpdate + 900000000) {
            snake = new Snake(2, 2, Direction.RIGHT, snake.getBody().size());
            ouch = false;
        }
        if (!ouch && now > snake.lastUpdate + snake.getSpeed()) {
            snake.lastUpdate = now;
            snake.move();
            BodyPart head = snake.head();
            if (head.getX() < 1) {
                head.setX(Setting.WORLD_WIDTH);
            }
            if (head.getX() > Setting.WORLD_WIDTH) {
                head.setX(1);
            }
            if (head.getY() < 1) {
                head.setY(Setting.WORLD_HEIGHT - 2);
            }
            if (head.getY() > Setting.WORLD_HEIGHT - 2) {
                head.setY(1);
            }
            if (fruit != null) {
                if (head.getX() == fruit.getX() && head.getY() == fruit.getY()) {
                    if (fruit.getIncreaseBody() == 2) {
                        if (Setting.playSound && Resource.s_p_2 != null) {
                            Sound.play1Sound(Resource.s_p_2);
                        }
                    } else {
                        if (Setting.playSound && Resource.s_p_1 != null) {
                            Sound.play1Sound(Resource.s_p_1);
                        }
                    }
                    for (int i = 0; i < fruit.getIncreaseBody(); i++) {
                        ArrayList<BodyPart> body = snake.getBody();
                        BodyPart lastPart = body.get(body.size() - 1);
                        body.add(new BodyPart(lastPart.getX(), lastPart.getY()));
                    }
                    CreateGame.kolB += fruit.getIncreaseBody();
                    snake.setSpeed(snake.getSpeed() - fruit.getIncreaseSpeed());
                    if (!end) {
                        placeFruit();
                    } else {
                        fruit = null;
                    }
                }
            }
        }
        if (fruit != null) {
            if (fruit.lifeTime > 0) {
                fruit.lifeTime -= (now - fruit.lastUpdate);
                fruit.lastUpdate = System.nanoTime();
            } else {
                fruit.blink = true;
            }
            if (fruit.blink && (now > fruit.lastUpdate)) {
                fruit.paint = !fruit.paint;
            }
            if (fruit.blink && (now > fruit.lastUpdate + 2000000000)) {
                if (!end) {
                    placeFruit();
                } else {
                    fruit = null;
                }
            }
        }

        Stone s1 = lsStone.get(lsStone.size() - 1);
        Stone s2 = lsStone.get(lsStone.size() - 2);
        if (!end) {
            s1.lastUpdate = System.nanoTime();
        }
        if (end && !setEnd) {
            s1.blink = true;
        }
        if (s1.blink && (now > s1.lastUpdate)) {
            if (s1.paint) {
                s1.paint = false;
                s2.paint = false;
            } else {
                s1.paint = true;
                s2.paint = true;
            }
        }
        if (s1.blink && (now > s1.lastUpdate + 2000000000) && !setEnd) {
            s1.setX(s1.getX() + 1);
            s1.blink = false;
            s2.setX(s2.getX() - 1);
            s2.blink = false;
            s1.paint = true;
            s2.paint = true;
            setEnd = true;
        }
    }


    @Override
    public void draw(Graphics2D g) {
        g.drawImage(Resource.bGround1, 0, Setting.CELL_SIZE * 2, CreateGame.getGame().getWidth(), CreateGame.getGame().getHeight() - Setting.CELL_SIZE * 2, null);
        drawBoard(g);
        drawFruit(g);
        drawStone(g);
        drawSnake(g);
        if (!CreateGame.getGame().update) {
            g.drawImage(Resource.pause, Setting.CELL_SIZE * 11, Setting.CELL_SIZE * 8,
                    CreateGame.getFrame().getWidth() - Setting.CELL_SIZE * 22, CreateGame.getFrame().getHeight() - Setting.CELL_SIZE * 16, null);
        }
        if (brief < 3)
            brief++;
        if (brief == 2) {
            new Briefing();
        }
    }

    private void drawBoard(Graphics2D g) {
        g.drawImage(Resource.board, 0, 0, CreateGame.getGame().getWidth(), Setting.CELL_SIZE * 2, null);
        g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE - 10));
        g.drawString("Уровень " + (CreateGame.getCurrLvl() + 1), Setting.CELL_SIZE, Setting.CELL_SIZE / 2 + (Setting.CELL_SIZE - 8));

        g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE - 5));
        g.drawImage(Resource.h, Setting.CELL_SIZE * 7, Setting.CELL_SIZE / 2, Setting.CELL_SIZE, Setting.CELL_SIZE, null);
        g.drawString("  x " + CreateGame.kolH, Setting.CELL_SIZE * 8 - (Setting.CELL_SIZE - 20), Setting.CELL_SIZE / 2 + (Setting.CELL_SIZE - 8));

        g.drawImage(Resource.fruit.get(1), Setting.CELL_SIZE * 11, Setting.CELL_SIZE / 2, Setting.CELL_SIZE, Setting.CELL_SIZE, null);
        g.drawString("  x " + CreateGame.kolB + " / " + (CreateGame.getCurrLvl() * 10 + 30), Setting.CELL_SIZE * 12 - (Setting.CELL_SIZE - 20), Setting.CELL_SIZE / 2 + (Setting.CELL_SIZE - 8));

        g.drawImage(Resource.cl, Setting.CELL_SIZE * 18, Setting.CELL_SIZE / 2, Setting.CELL_SIZE, Setting.CELL_SIZE, null);
        g.drawString("  " + (int) time + " c", Setting.CELL_SIZE * 19 - (Setting.CELL_SIZE - 20), Setting.CELL_SIZE / 2 + (Setting.CELL_SIZE - 8));
    }

    private void drawSnake(Graphics2D g) {
        BodyPart p = snake.getBody().get(0);
        int angle = 0;
        double rotationRequired;
        BufferedImage headSt;
        if (snake.getDirection().getX() != -1) {
            if (snake.getDirection().getX() == 0 && snake.getDirection().getY() == 1) {
                angle = 270;
            }
            if (snake.getDirection().getX() == 0 && snake.getDirection().getY() == -1) {
                angle = 90;
            }

            if (fruit != null && ((p.getX() + 1 == fruit.getX() && p.getY() == fruit.getY()) || (p.getX() - 1 == fruit.getX() && p.getY() == fruit.getY())
                    || (p.getX() == fruit.getX() && p.getY() + 1 == fruit.getY()) || (p.getX() == fruit.getX() && p.getY() - 1 == fruit.getY())
                    || (p.getX() + 1 > Setting.WORLD_WIDTH && fruit.getX() == 1 && p.getY() == fruit.getY())
                    || (p.getY() + 1 > Setting.WORLD_HEIGHT - 2 && fruit.getY() == 1 && p.getX() == fruit.getX())
                    || (p.getY() - 1 < 1 && fruit.getY() == Setting.WORLD_HEIGHT - 2 && p.getX() == fruit.getX()))) {
                headSt = Resource.headA0;
            } else {
                headSt = Resource.head0;
            }
            rotationRequired = Math.toRadians(angle);
            double locationX = headSt.getWidth() / 2;
            double locationY = headSt.getHeight() / 2;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            g.drawImage(op.filter(headSt, null), p.getX() * Setting.CELL_SIZE - Setting.CELL_SIZE,
                    CreateGame.getGame().getScreenSize().height - (p.getY() * Setting.CELL_SIZE),
                    Setting.CELL_SIZE,
                    Setting.CELL_SIZE, null);
        } else {
            if (fruit != null && ((p.getX() - 1 == fruit.getX() && p.getY() == fruit.getY())
                    || (p.getX() - 1 < 1 && fruit.getX() == Setting.WORLD_WIDTH && p.getY() == fruit.getY()))) {
                headSt = Resource.headA180;
            } else {
                headSt = Resource.head180;
            }
            g.drawImage(headSt, p.getX() * Setting.CELL_SIZE - Setting.CELL_SIZE,
                    CreateGame.getGame().getScreenSize().height - (p.getY() * Setting.CELL_SIZE),
                    Setting.CELL_SIZE, Setting.CELL_SIZE, null);
        }
        ArrayList<BodyPart> lsBody = snake.getBody();
        for (int i = 1; i < lsBody.size(); i++) {
            BodyPart prev = lsBody.get(i - 1);
            BufferedImage bodyImage = Resource.body;
            if ((prev.getX() + 1 == lsBody.get(i).getX() && prev.getY() == lsBody.get(i).getY())
                    || (prev.getX() - 1 == lsBody.get(i).getX() && prev.getY() == lsBody.get(i).getY())
                    || (prev.getX() - (Setting.WORLD_WIDTH - 1) == lsBody.get(i).getX() && prev.getY() == lsBody.get(i).getY())
                    || (prev.getX() + (Setting.WORLD_WIDTH - 1) == lsBody.get(i).getX() && prev.getY() == lsBody.get(i).getY())) {
                angle = 0;
            }
            if ((prev.getY() - 1 == lsBody.get(i).getY() && prev.getX() == lsBody.get(i).getX())
                    || (prev.getY() + Setting.WORLD_HEIGHT - 3 == lsBody.get(i).getY() && prev.getX() == lsBody.get(i).getX())) {
                angle = 270;
            }
            if ((prev.getY() + 1 == lsBody.get(i).getY() && prev.getX() == lsBody.get(i).getX())
                    || (prev.getY() - (Setting.WORLD_HEIGHT - 3) == lsBody.get(i).getY() && prev.getX() == lsBody.get(i).getX())) {
                angle = 90;
            }
            if ((i < lsBody.size() - 1) && (lsBody.get(i + 1).getX() != lsBody.get(i - 1).getX()
                    && lsBody.get(i + 1).getY() != lsBody.get(i - 1).getY())) {
                bodyImage = Resource.body2;
            }
            double locationX = bodyImage.getWidth() / 2;
            double locationY = bodyImage.getHeight() / 2;
            rotationRequired = Math.toRadians(angle);
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            g.drawImage(op.filter(bodyImage, null), lsBody.get(i).getX() * Setting.CELL_SIZE - Setting.CELL_SIZE,
                    CreateGame.getGame().getScreenSize().height - (lsBody.get(i).getY() * Setting.CELL_SIZE),
                    Setting.CELL_SIZE,
                    Setting.CELL_SIZE, null);
        }
        if (ouch) {
            g.drawImage(Resource.ouch, p.getX() * Setting.CELL_SIZE - Setting.CELL_SIZE,
                    CreateGame.getGame().getScreenSize().height - ((p.getY() + 1) * Setting.CELL_SIZE), Setting.CELL_SIZE, Setting.CELL_SIZE, null);
        }
    }

    private void drawFruit(Graphics2D g) {
        if (!end && fruit != null && fruit.paint) {
            g.drawImage(Resource.fruit.get(fruit.numFruit), fruit.getX() * Setting.CELL_SIZE - Setting.CELL_SIZE,
                    CreateGame.getGame().getScreenSize().height - (fruit.getY() * Setting.CELL_SIZE),
                    Setting.CELL_SIZE,
                    Setting.CELL_SIZE, null);
        }
    }

    private void drawStone(Graphics2D g) {
        for (Stone stone : lsStone) {
            if (stone.paint) {
                g.drawImage(Resource.stone, stone.getX() * Setting.CELL_SIZE - Setting.CELL_SIZE,
                        CreateGame.getGame().getScreenSize().height - (stone.getY() * Setting.CELL_SIZE),
                        Setting.CELL_SIZE, Setting.CELL_SIZE, null);
            }
        }
        Stone s = lsStone.get(lsStone.size() - 2);
        if (!s.paint) {
            g.drawImage(Resource.exit, s.getX() * Setting.CELL_SIZE - Setting.CELL_SIZE,
                    CreateGame.getGame().getScreenSize().height - (s.getY() * Setting.CELL_SIZE),
                    Setting.CELL_SIZE * 2, Setting.CELL_SIZE, null);
        }
        if (setEnd) {
            g.drawImage(Resource.exit, (s.getX() + 1) * Setting.CELL_SIZE - Setting.CELL_SIZE,
                    CreateGame.getGame().getScreenSize().height - (s.getY() * Setting.CELL_SIZE),
                    Setting.CELL_SIZE * 2, Setting.CELL_SIZE, null);
        }
    }

    private void placeFruit() {
        int x = 1 + (int) (Math.random() * Setting.WORLD_WIDTH);
        int y = 1 + (int) (Math.random() * (Setting.WORLD_HEIGHT - 2));
        while (!isCellEmpty(x, y)) {
            if (x < Setting.WORLD_WIDTH) {
                x++;
            } else {
                if (y < Setting.WORLD_HEIGHT - 2) {
                    x = 1;
                    y++;
                } else {
                    x = 1;
                    y = 1;
                }
            }
        }
        fruit = new Fruit(x, y);
    }

    private boolean isCellEmpty(int x, int y) {
        for (BodyPart bodyPart : snake.getBody()) {
            if (bodyPart.getX() == x && bodyPart.getY() == y) {
                return false;
            }
        }
        for (Stone s : lsStone) {
            if (s.getX() == x && s.getY() == y) {
                return false;
            }
        }
        return true;
    }

    private void processInput() {
        for (KeyEvent event : CreateGame.getGame().getInput().getKeyEvents()) {
            if (event.getKeyCode() == Setting.keyUP && snake.getDirection() != Direction.DOWN) {
                snake.setDirection(Direction.UP);
            }
            if (event.getKeyCode() == Setting.keyRIGHT && snake.getDirection() != Direction.LEFT) {
                snake.setDirection(Direction.RIGHT);
            }
            if (event.getKeyCode() == Setting.keyDOWN && snake.getDirection() != Direction.UP) {
                snake.setDirection(Direction.DOWN);
            }
            if (event.getKeyCode() == Setting.keyLEFT && snake.getDirection() != Direction.RIGHT) {
                snake.setDirection(Direction.LEFT);
            }
            if (event.getKeyCode() == 27) {
                CreateGame.cont = new Continue(this, CreateGame.getCurrLvl(), CreateGame.kolH, CreateGame.kolB, CreateGame.score, CreateGame.time);
                CreateGame.playMusic(1);
                CreateGame.getGame().setScene(new MainMenu());
            }
            if (event.getKeyCode() == 78 && CreateGame.music != null) {
                CreateGame.music.next();
            }
        }
    }

    private boolean isEndScene() {
        if (CreateGame.getCurrLvl() * 10 + 29 < CreateGame.kolB) {
            end = true;
            Stone s1 = lsStone.get(lsStone.size() - 2);
            Stone s2 = lsStone.get(lsStone.size() - 1);
            if ((snake.head().getX() == s1.getX() + 1 && snake.head().getY() == s1.getY()) || (snake.head().getX() == s2.getX() - 1 && snake.head().getY() == s2.getY())) {
                end = false;
                if (CreateGame.getCurrLvl() >= CreateGame.getKolLvl() - 1) {
                    CreateGame.getGame().setScene(new OverScene(2, calcScore(), time));
                    CreateGame.playMusic(3);
                } else {
                    CreateGame.getGame().setScene(new OverScene(1, calcScore(), time));
                }
                return true;
            }
        }
        return false;
    }

    private int calcScore() {
        int x = 0;
        if (time > 600)
            x = 3;
        if (time < 600)
            x = 2;
        if (time < 300)
            x = 1;
        long speed = ((300000000 - (50000000L * CreateGame.getCurrLvl())) - snake.getSpeed()) / 20000000;
        double score = (CreateGame.kolB * 200 + CreateGame.kolH * 1000 + (int) speed * 500) / x;
        return (int) score;
    }

    private boolean isGameOver() {
        if (snake.getBody().size() == Setting.WORLD_WIDTH * Setting.WORLD_HEIGHT) {
            return true;
        }
        for (BodyPart bodyPart : snake.getBody()) {
            if (!ouch && bodyPart != snake.head() && snake.head().getX() == bodyPart.getX() && snake.head().getY() == bodyPart.getY()) {
                if (Setting.playSound && Resource.s_ston != null) {
                    Sound.play1Sound(Resource.s_ston);
                }
                CreateGame.kolH--;
                if (CreateGame.kolH > 0) {
                    ouch = true;
                }
            }
        }
        for (Stone s : lsStone) {
            if (!ouch && snake.head().getX() == s.getX() && snake.head().getY() == s.getY()) {
                if (Setting.playSound && Resource.s_ston != null) {
                    Sound.play1Sound(Resource.s_ston);
                }
                CreateGame.kolH--;
                if (CreateGame.kolH > 0) {
                    ouch = true;
                }
            }
        }
        return CreateGame.kolH < 1;
    }


    public void click(int x, int y) {
    }

    public Fruit getFruit() {
        return fruit;
    }

    public Snake getSnake() {
        return snake;
    }

}
