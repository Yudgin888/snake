import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class OverScene extends SuperScene {
    private final int score;
    private final double time;
    public int numScene;
    private int dialog;

    public OverScene(int numScene, int score, double time) {
        this.score = score;
        CreateGame.score += score;
        this.time = time;
        CreateGame.time += time;
        this.numScene = numScene;
        dialog = 0;
    }

    public void update(long nanosPassed) {
        for (KeyEvent event : CreateGame.getGame().getInput().getKeyEvents()) {
            if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                if (numScene == 1) {
                    CreateGame.setNextScene();
                } else {
                    CreateGame.getGame().setScene(new MainMenu());
                    CreateGame.playMusic(1);
                }
            }
            if (event.getKeyCode() == 27) {
                CreateGame.getGame().setScene(new MainMenu());
                CreateGame.playMusic(1);
            }
        }
    }

    public void draw(Graphics2D g) {
        if (numScene == 1) {
            g.drawImage(Resource.bGroundOv, 0, 0, CreateGame.getGame().getWidth(), CreateGame.getGame().getHeight(), null);
            g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE - Setting.CELL_SIZE / 6));
            g.drawString("Вы завершили " + (CreateGame.getCurrLvl() + 1) + " уровень!", Setting.CELL_SIZE * 8 + Setting.CELL_SIZE / 4, Setting.CELL_SIZE * 7 + Setting.CELL_SIZE / 2);

            g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE - Setting.CELL_SIZE / 6));
            g.drawString("Результат:", Setting.CELL_SIZE * 11 + Setting.CELL_SIZE / 4, Setting.CELL_SIZE * 9 + Setting.CELL_SIZE / 2);

            g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE - Setting.CELL_SIZE / 6));
            g.drawString("Время: " + (int) time + "c", Setting.CELL_SIZE * 9, Setting.CELL_SIZE * 11);

            g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE - Setting.CELL_SIZE / 6));
            g.drawString("Набрано очков: " + score, Setting.CELL_SIZE * 9, Setting.CELL_SIZE * 12);

            g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE / 2));
            g.drawString("Для продолжения нажмите Enter", Setting.CELL_SIZE * 9 + Setting.CELL_SIZE / 2, Setting.CELL_SIZE * 16);
        }
        if (numScene == 2) {
            g.drawImage(Resource.bFin, 0, 0, CreateGame.getGame().getWidth(), CreateGame.getGame().getHeight(), null);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE - Setting.CELL_SIZE / 6));
            g.drawString("Общее время: " + (int) time + "c", Setting.CELL_SIZE * 9, Setting.CELL_SIZE * 2);

            g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE - Setting.CELL_SIZE / 6));
            g.drawString("Всего набрано очков: " + score, Setting.CELL_SIZE * 9, Setting.CELL_SIZE * 3 + Setting.CELL_SIZE / 2);
            if (dialog < 3)
                dialog++;
            if (dialog == 2) {
                new SaveDialog();
            }
        }
        if (numScene == 3) {
            g.drawImage(Resource.gameOver, 0, 0, CreateGame.getGame().getWidth(), CreateGame.getGame().getHeight(), null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE - Setting.CELL_SIZE / 6));
            g.drawString("Общее время: " + (int) CreateGame.time + "c", Setting.CELL_SIZE * 9, Setting.CELL_SIZE * 2);

            g.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE - Setting.CELL_SIZE / 6));
            g.drawString("Всего набрано очков: " + CreateGame.score, Setting.CELL_SIZE * 9, Setting.CELL_SIZE * 3 + Setting.CELL_SIZE / 2);
            if (dialog < 3)
                dialog++;
            if (dialog == 2) {
                new SaveDialog();
            }
        }
    }

    public void click(int x, int y) {
    }

}


class SaveDialog extends JDialog {

    public SaveDialog() {
        super(new JFrame(), true);
        setIconImage(Resource.icon);
        setResizable(false);
        setBounds(CreateGame.getFrame().getX() + Setting.CELL_SIZE * 9, CreateGame.getFrame().getY() + Setting.CELL_SIZE * 15,
                CreateGame.getFrame().getWidth() - Setting.CELL_SIZE * 19, CreateGame.getFrame().getHeight() - Setting.CELL_SIZE * 16);
        setContentPane(new MyPanel(Resource.bGroundset));

        JPanel pan = new JPanel();
        pan.setOpaque(false);
        pan.setLayout(new BorderLayout());

        JPanel pan1 = new JPanel();
        pan1.setOpaque(false);
        JLabel label = new JLabel("Для сохранения результата введите имя:");
        label.setFont(new Font("Arial", Font.PLAIN, Setting.CELL_SIZE / 3));
        pan1.add(label);

        final JTextField txt = new JTextField(10);
        JPanel pan2 = new JPanel();
        pan2.setOpaque(false);
        pan2.add(txt);

        final JButton but = new JButton("Сохранить");
        but.setEnabled(false);
        JPanel pan3 = new JPanel();
        pan3.setOpaque(false);
        pan3.add(but);

        pan.add(pan1, BorderLayout.NORTH);
        pan.add(pan2, BorderLayout.CENTER);
        pan.add(pan3, BorderLayout.SOUTH);
        add(pan);

        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                String str;
                if ((int) (e.getKeyChar()) == 8) {
                    str = txt.getText();
                } else {
                    str = txt.getText() + e.getKeyChar();
                }
                but.setEnabled(str.length() > 0);
            }
        });

        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str_tmp = txt.getText();
                if (str_tmp.length() > 10) {
                    str_tmp = str_tmp.substring(0, 9);
                }
                String[] str = {str_tmp, String.valueOf(CreateGame.score), String.valueOf((int) CreateGame.time) + " c"};
                CreateGame.lsScore.add(str);
                dispose();
                CreateGame.getGame().setScene(new MainMenu());
                CreateGame.playMusic(1);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CreateGame.getGame().setScene(new MainMenu());
                CreateGame.playMusic(1);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 27) {
                    dispose();
                    CreateGame.getGame().setScene(new MainMenu());
                    CreateGame.playMusic(1);
                }
            }
        });
        setUndecorated(true);
        setFocusable(true);
        setVisible(true);
    }
}