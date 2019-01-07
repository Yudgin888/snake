import java.io.Serializable;


public class Fruit implements Serializable {
    private int x;
    private int y;
    private long increaseSpeed;
    private int increaseBody;
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
        int val = (int)(Math.random()*100);
        if(val <= 50){
            increaseBody = 1;
            increaseSpeed = 0;
            lifeTime = new Long(700000000);
            lifeTime *= 10;
            numFruit = 0;
        }
        else if(val >= 75){
            increaseBody = 2;
            increaseSpeed = 0;
            lifeTime = new Long(300000000);
            lifeTime *= 10;
            numFruit = 1;
        }else{
            increaseBody = 0;
            increaseSpeed = 20000000;
            lifeTime = new Long(500000000);
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

    public long getIncreaseSpeed(){
        return increaseSpeed;
    }

    public int getIncreaseBody(){
        return increaseBody;
    }
}

