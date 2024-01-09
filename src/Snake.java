import java.io.Serializable;
import java.util.ArrayList;


public class Snake implements Serializable {
    private final ArrayList<BodyPart> body;
    private Direction direction;
    private long speed;
    public long lastUpdate;

    public Snake(int x, int y, Direction direction, int kol) {
        this.direction = direction;
        speed = 300000000 - (40000000L * CreateGame.getCurrLvl());
        if (speed < 50000000) {
            speed = 50000000;
        }
        body = new ArrayList<BodyPart>();
        for (int i = 0; i < kol; i++) {
            body.add(new BodyPart(x - direction.getX() * i, y - direction.getY() * i));
        }
    }

    public void move() {
        for (int i = body.size() - 1; i > 0; i--) {
            BodyPart curr = body.get(i);
            BodyPart prev = body.get(i - 1);
            curr.setX(prev.getX());
            curr.setY(prev.getY());
        }
        head().setX(head().getX() + direction.getX());
        head().setY(head().getY() + direction.getY());
    }

    public BodyPart head() {
        return body.get(0);
    }

    public ArrayList<BodyPart> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

}

class BodyPart implements Serializable {
    private int x;
    private int y;

    public BodyPart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
