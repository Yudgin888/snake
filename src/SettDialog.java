import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SettDialog extends JDialog {
    private boolean sound;
    private int keyU;
    private int keyD;
    private int keyL;
    private int keyR;

    public SettDialog(JFrame frame) {
        super(frame, "Настройки", true);
        sound = Setting.playSound;
        keyU = Setting.keyUP;
        keyD = Setting.keyDOWN;
        keyL = Setting.keyLEFT;
        keyR = Setting.keyRIGHT;

        setIconImage(Resource.iconSett);
        setResizable(false);
        setBounds(CreateGame.getFrame().getX() + Setting.CELL_SIZE * 6, CreateGame.getFrame().getY() + Setting.CELL_SIZE * 3,
                CreateGame.getFrame().getWidth() - Setting.CELL_SIZE * 13, CreateGame.getFrame().getHeight() - Setting.CELL_SIZE * 9);
        setContentPane(new MyPanel(Resource.bGroundset));

        JButton ok = new JButton("Сохранить");
        JButton can = new JButton("Отмена");

        JLabel lSound = new JLabel("Звук");
        lSound.setFont(new Font("Arial", Font.ROMAN_BASELINE, Setting.CELL_SIZE/2));
        ButtonGroup group = new ButtonGroup();
        final JCheckBox v1 = new JCheckBox("вкл");
        final JCheckBox v2 = new JCheckBox("выкл");
        v1.setOpaque(false);
        v2.setOpaque(false);
        group.add(v1);
        group.add(v2);
        v1.setSelected(Setting.playSound);
        v1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (sound) {
                    sound = false;
                } else {
                    sound = true;
                }
            }
        });
        v2.setSelected(!Setting.playSound);

        JLabel lSize = new JLabel("Размер окна");
        lSize.setFont(new Font("Arial", Font.ROMAN_BASELINE, Setting.CELL_SIZE/2));
        final Choice chSize = new Choice();
        for (Integer i : Setting.lsSize) {
            chSize.add(i * Setting.WORLD_WIDTH + "x" + i * Setting.WORLD_HEIGHT);
        }
        chSize.select(Setting.CELL_SIZE * Setting.WORLD_WIDTH + "x" + Setting.CELL_SIZE * Setting.WORLD_HEIGHT);

        JLabel lDir = new JLabel("Управление:");
        lSize.setFont(new Font("Arial", Font.ROMAN_BASELINE, Setting.CELL_SIZE - 10));
        JLabel lUp = new JLabel("Вверх");
        lSize.setFont(new Font("Arial", Font.ROMAN_BASELINE, Setting.CELL_SIZE - 10));
        JLabel lDown = new JLabel("Вниз");
        lSize.setFont(new Font("Arial", Font.ROMAN_BASELINE, Setting.CELL_SIZE - 10));
        JLabel lLeft = new JLabel("Влево");
        lSize.setFont(new Font("Arial", Font.ROMAN_BASELINE, Setting.CELL_SIZE - 10));
        JLabel lRight = new JLabel("Вправо");
        lSize.setFont(new Font("Arial", Font.ROMAN_BASELINE, Setting.CELL_SIZE - 10));
        final JTextField up = new JTextField(4);
        up.setText(KeyEvent.getKeyText(keyU));
        final JTextField down = new JTextField(4);
        down.setText(KeyEvent.getKeyText(keyD));
        final JTextField left = new JTextField(4);
        left.setText(KeyEvent.getKeyText(keyL));
        final JTextField right = new JTextField(4);
        right.setText(KeyEvent.getKeyText(keyR));

        JPanel butPan = new JPanel();
        butPan.setLayout(new BoxLayout(butPan, BoxLayout.LINE_AXIS));
        butPan.setOpaque(false);
        butPan.add(ok);
        butPan.add(Box.createHorizontalStrut(Setting.CELL_SIZE));
        butPan.add(can);
        JPanel BPan = new JPanel();
        BPan.setOpaque(false);
        BPan.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        BPan.add(butPan);

        JPanel sizePan = new JPanel();
        sizePan.setOpaque(false);
        sizePan.setLayout(new BoxLayout(sizePan, BoxLayout.LINE_AXIS));
        sizePan.add(lSize);
        sizePan.add(Box.createHorizontalStrut(30));
        sizePan.add(chSize);
        JPanel soundPan = new JPanel();
        soundPan.setOpaque(false);
        soundPan.setLayout(new BoxLayout(soundPan, BoxLayout.PAGE_AXIS));
        soundPan.add(lSound);
        soundPan.add(v1);
        soundPan.add(v2);

        JPanel firstPan = new JPanel();
        firstPan.setLayout(new BoxLayout(firstPan, BoxLayout.LINE_AXIS));
        firstPan.setOpaque(false);
        firstPan.add(sizePan);
        firstPan.add(Box.createHorizontalStrut(10));
        firstPan.add(soundPan);
        JPanel FPan = new JPanel();
        FPan.setOpaque(false);
        FPan.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        FPan.add(firstPan);

        JPanel name = new JPanel();
        name.setOpaque(false);
        name.add(lDir);
        JPanel lr = new JPanel();
        lr.setOpaque(false);
        lr.setLayout(new BoxLayout(lr, BoxLayout.PAGE_AXIS));
        lr.add(lLeft);
        lr.add(left);
        lr.add(Box.createVerticalStrut(10));
        lr.add(lRight);
        lr.add(right);
        JPanel ud = new JPanel();
        ud.setOpaque(false);
        ud.setLayout(new BoxLayout(ud, BoxLayout.PAGE_AXIS));
        ud.add(lUp);
        ud.add(up);
        ud.add(Box.createVerticalStrut(10));
        ud.add(lDown);
        ud.add(down);
        JPanel centr = new JPanel();
        centr.setOpaque(false);
        centr.setLayout(new BoxLayout(centr, BoxLayout.LINE_AXIS));
        centr.add(name);
        centr.add(Box.createHorizontalStrut(Setting.CELL_SIZE));
        centr.add(lr);
        centr.add(Box.createHorizontalStrut(Setting.CELL_SIZE));
        centr.add(ud);
        JPanel CPan = new JPanel();
        CPan.setOpaque(false);
        CPan.add(centr);

        setLayout(new BorderLayout());
        add(FPan, BorderLayout.NORTH);
        add(CPan, BorderLayout.CENTER);
        add(BPan, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });

        up.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                up.setText("");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                up.setText(KeyEvent.getKeyText(keyU));
            }
        });

        up.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                int key = e.getKeyCode();
                if(key != 27 && key != 525 && key != 524 && key != 80 && key != 78 && key != 32){
                    keyU = key;
                }else{
                    up.setText(KeyEvent.getKeyText(keyU));
                }
            }
        });

        down.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                down.setText("");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                down.setText(KeyEvent.getKeyText(keyD));
            }
        });

        down.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                int key = e.getKeyCode();
                if(key != 27 && key != 525 && key != 524 && key != 80 && key != 78 && key != 32){
                    keyD = key;
                }else{
                    down.setText(KeyEvent.getKeyText(keyD));
                }
            }
        });

        left.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                left.setText("");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                left.setText(KeyEvent.getKeyText(keyL));
            }
        });

        left.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                int key = e.getKeyCode();
                if(key != 27 && key != 525 && key != 524 && key != 80 && key != 78 && key != 32){
                    keyL = key;
                }else{
                    left.setText(KeyEvent.getKeyText(keyL));
                }
            }
        });

        right.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                right.setText("");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                right.setText(KeyEvent.getKeyText(keyR));
            }
        });

        right.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                int key = e.getKeyCode();
                if(key != 27 && key != 525 && key != 524 && key != 80 && key != 78 && key != 32){
                    keyR = key;
                }else{
                    right.setText(KeyEvent.getKeyText(keyR));
                }
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Setting.playSound = sound;
                if(!sound){
                    CreateGame.music.stopThread();
                    CreateGame.music = null;
                }else{
                    CreateGame.playMusic(1);
                }
                Setting.keyUP = keyU;
                Setting.keyDOWN = keyD;
                Setting.keyLEFT = keyL;
                Setting.keyRIGHT = keyR;
                dispose();
                if(Setting.CELL_SIZE != Setting.lsSize.get(chSize.getSelectedIndex())) {
                    Setting.CELL_SIZE = Setting.lsSize.get(chSize.getSelectedIndex());
                    CreateGame.createNewFrame();
                }
            }
        });

        can.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
}
