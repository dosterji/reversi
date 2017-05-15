package plu.blue.reversi.client.gui;import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Miguel on 5/9/17.
 */
public class startMenu extends JFrame implements ActionListener{
    private GameWindow gui;
    private ReversiMenuBar men;

    private JFrame frame = new JFrame("REVERSI");
    private JPanel panel = new JPanel();

    private JButton btn1 = new JButton("Human vs. Human");
    private JButton btn2 = new JButton("Human vs Computer");
    private JButton btn3 = new JButton("Online");
    private JButton btn4 = new JButton("Load Game");

    public startMenu() {
        panel.setLayout(new GridLayout(4, 1));
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);

        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);

        frame.add(panel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setResizable(false);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn1) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {new GameWindow(false);}
            });

        }

        if(e.getSource() == btn2) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    gui = new GameWindow(true);
                    //men = new ReversiMenuBar(gui = new GameWindow(true));

                    //int diff = men.chooseDiff();
                    //gui.newCPUGame(diff);
                }
            });

        }
        if(e.getSource() == btn4){

        }

    }

}
