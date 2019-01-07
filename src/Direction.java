import java.io.Serializable;

/**
 * Created by Yudgin on 11.11.2014.
 */
public enum Direction implements Serializable {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public int getX() {
        switch (this) {
            case LEFT:
                return -1;
            case RIGHT:
                return 1;
            default:
                return 0;
        }
    }

    public int getY() {
        switch (this) {
            case UP:
                return 1;
            case DOWN:
                return -1;
            default:
                return 0;
        }
    }
}
