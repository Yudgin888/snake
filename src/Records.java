import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Records extends JDialog {

    public Records(JFrame frame) {
        super(frame, "Рекорды", true);
        setIconImage(Resource.rec);
        setResizable(false);
        setBounds(CreateGame.getFrame().getX() + Setting.CELL_SIZE * 6, CreateGame.getFrame().getY() + Setting.CELL_SIZE * 3,
                CreateGame.getFrame().getWidth() - Setting.CELL_SIZE * 13, CreateGame.getFrame().getHeight() - Setting.CELL_SIZE * 9);
        setContentPane(new MyPanel(Resource.bGroundset));

        String[] columnNames = {"Имя", "Набрано очков", "Время"};
        CreateGame.sortScore();
        String[][] data;
        if(CreateGame.lsScore != null) {
            data = new String[CreateGame.lsScore.size() + 50][3];
            for (int i = 0; i < CreateGame.lsScore.size(); i++) {
                String[] tmp = CreateGame.lsScore.get(i);
                data[i][0] = (i + 1) + ". " + tmp[0];
                data[i][1] = tmp[1];
                data[i][2] = tmp[2];
            }

        }else{
            data = new String[0][3];
        }

        JPanel pan = new JPanel();
        pan.setOpaque(false);
        pan.setBorder(BorderFactory.createEmptyBorder(5, 50, 5, 50));

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        table.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(Setting.CELL_SIZE * 9, Setting.CELL_SIZE * 7));

        pan.add(scrollPane);

        JButton but = new JButton("Ok");
        JPanel panBut = new JPanel();
        panBut.setOpaque(false);
        panBut.add(but);

        JPanel pan1 = new JPanel();
        pan1.setLayout(new BorderLayout());
        pan1.setOpaque(false);
        pan1.add(pan, BorderLayout.CENTER);
        pan1.add(panBut, BorderLayout.SOUTH);
        add(pan1);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });

        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 27){
                    dispose();
                }
            }
        });
        setFocusable(true);
        setVisible(true);
    }

}
