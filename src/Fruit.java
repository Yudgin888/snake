import java.io.Serializable;


public class Fruit implements Serializable {
    private final int x;
    private final int y;
    private final long increaseSpeed;
    private final int increaseBody;
    public Long lifeTime;
    public long lastUpdate;
    public int numFruit;
    public boolean paint;
    public boolean blink;


    public Fruit(int x, int y) {
        this.x = x;
        this.y = y;
        lastUpdate = System.nanoTime();
        paint = true;
        blink = false;
        int val = (int) (Math.random() * 100);
        if (val <= 50) {
            increaseBody = 1;
            increaseSpeed = 0;
            lifeTime = 700000000L;
            lifeTime *= 10;
            numFruit = 0;
        } else if (val >= 75) {
            increaseBody = 2;
            increaseSpeed = 0;
            lifeTime = 300000000L;
            lifeTime *= 10;
            numFruit = 1;
        } else {
            increaseBody = 0;
            increaseSpeed = 20000000;
            lifeTime = 500000000L;
            lifeTime *= 10;
            numFruit = 2;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getIncreaseSpeed() {
        return increaseSpeed;
    }

    public int getIncreaseBody() {
        return increaseBody;
    }
}

