import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


class MyPanel extends JPanel {
    public BufferedImage im;

    MyPanel(BufferedImage im){
        this.im = im;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(im, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}