import java.awt.*;

/**
 * Created by Yudgin on 11.11.2014.
 */
public abstract class SuperScene {

    public abstract void update(long time);

    public abstract void draw(Graphics2D g2d);

    public abstract void click(int x, int y);
}

