import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Briefing extends JDialog {

    Briefing() {
        super(new JFrame(), "Уровень " + (CreateGame.getCurrLvl() + 1), true);
        setIconImage(Resource.icon);
        setResizable(false);
        setBounds(CreateGame.getFrame().getX() + Setting.CELL_SIZE * 9, CreateGame.getFrame().getY() + Setting.CELL_SIZE * 6,
                CreateGame.getFrame().getWidth() - Setting.CELL_SIZE * 17, CreateGame.getFrame().getHeight() - Setting.CELL_SIZE * 13);
        setContentPane(new MyPanel(Resource.briefing));

        JLabel label1 = new JLabel("Цель: собрать " + (CreateGame.getCurrLvl() * 10 + 30) + " яблок");
        label1.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE / 2));
        JPanel lPan = new JPanel();
        lPan.setOpaque(false);
        lPan.setLayout(new BorderLayout());
        lPan.add(label1);

        JButton but = new JButton("ОК");
        but.setOpaque(false);
        JPanel butPan = new JPanel();
        butPan.setOpaque(false);
        butPan.setLayout(new BorderLayout());
        butPan.add(but);
        butPan.setBorder(BorderFactory.createEmptyBorder(Setting.CELL_SIZE * 2, Setting.CELL_SIZE * 2, 5, Setting.CELL_SIZE * 2));

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(Setting.CELL_SIZE * 2, 5, 5, 5));
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.add(lPan, BorderLayout.CENTER);
        panel.add(butPan, BorderLayout.SOUTH);
        add(panel);

        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CreateGame.getGame().sceneStart();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CreateGame.getGame().sceneStart();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10 || e.getKeyCode() == 27) {
                    dispose();
                    CreateGame.getGame().sceneStart();
                }
            }
        });
        setFocusable(true);
        setVisible(true);
    }
}
