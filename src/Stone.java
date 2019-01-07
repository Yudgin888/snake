import java.io.Serializable;

/**
 * Created by Yudgin on 22.11.2014.
 */

public class Stone implements Serializable{
    private int x;
    private int y;
    public long lastUpdate;
    public boolean paint;
    public boolean blink;


    public Stone(int x, int y) {
        this.x = x;
        this.y = y;
        paint = true;
        blink = false;
    }

    public Stone(Stone s){
        this.x = s.x;
        this.y = s.y;
        this.paint = s.paint;
        this.blink = s.blink;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}

