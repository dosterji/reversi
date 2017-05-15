package plu.blue.reversi.client.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * This is a menu that can be used for testing purposes.  Developers can add
 * and remove options in the menu as needed for testing.  Each menu item
 * should perform a specific test.  In production, this menu should NOT BE
 * VISIBLE to the user.
 */
public class DeveloperMenu extends JMenu implements ActionListener {

    private GameWindow gui;
    private JMenuItem testFlipAnimItem;
    private JMenuItem incWhiteItem;
    private int whiteScore;
    private JMenuItem changePlayer1NameItem;
    private JMenuItem swapActivePlayerItem;
    private JMenuItem displayMovesItem;
    private int activePlayer;
    private boolean activateDisplay = false;

    private JMenuItem testServerItem;

    /**
     * Initialize the developer menu
     *
     * @param gui the main GameWindow object
     */
    public DeveloperMenu(GameWindow gui) {
        whiteScore = 0;
        activePlayer = 1;

        this.gui = gui;
        this.setText("Developer");
        this.setMnemonic(KeyEvent.VK_D);
        this.getAccessibleContext().setAccessibleDescription("Developer options");

        testFlipAnimItem = new JMenuItem("Test flip animation" );
        testFlipAnimItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.META_MASK));
        testFlipAnimItem.addActionListener(this);
        this.add(testFlipAnimItem);

        incWhiteItem = new JMenuItem("Increment white score");
        incWhiteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.META_MASK));
        incWhiteItem.addActionListener(this);
        this.add(incWhiteItem);

        changePlayer1NameItem = new JMenuItem("Change Player 1 Name");
        changePlayer1NameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.META_MASK));
        changePlayer1NameItem.addActionListener(this);
        this.add(changePlayer1NameItem);

        swapActivePlayerItem = new JMenuItem("Swap Active Player");
        swapActivePlayerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.META_MASK));
        swapActivePlayerItem.addActionListener(this);
        this.add(swapActivePlayerItem);

        testServerItem = new JMenuItem("Test Server");
        testServerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        testServerItem.addActionListener(this);
        this.add(testServerItem);

        displayMovesItem = new JMenuItem("Display Available Moves: ");
        displayMovesItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        displayMovesItem.addActionListener(this);
        this.add(displayMovesItem);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == testFlipAnimItem) {
            testFlip();
        } else if (e.getSource() == incWhiteItem) {
            incWhiteScore();
        } else if( e.getSource() == changePlayer1NameItem) {
            changePlayer1Name();
        } else if( e.getSource() == swapActivePlayerItem ) {
            swapActivePlayer();
        } else if( e.getSource() == testServerItem ) {
            testServer();
        } else if( e.getSource() == displayMovesItem) {
            setMovesDisplay();
        }
    }

    private void testFlip() {
        gui.getBoardView().animateFlipSequence(5, 1, 1, 5,
                BoardView.CellColor.BLACK, BoardView.CellColor.WHITE, 300);
    }

    private void incWhiteScore() {
        whiteScore++;
        gui.getPlayerInfoPanel().setScore(2, whiteScore);
    }

    private void changePlayer1Name() {

        Object[] possibleP = { "Player 1", "Player 2"};
        Object selectedP = JOptionPane.showInputDialog(null,
                "Select Player", "Input",
                JOptionPane.QUESTION_MESSAGE, null,
                possibleP, possibleP[0]);

        if(selectedP.equals("Player 1")){
            String newName = JOptionPane.showInputDialog(gui, "New name");
            gui.getPlayerInfoPanel().setPlayerName(1, newName);
        }
        else if(selectedP.equals("Player 2")){
            String newName = JOptionPane.showInputDialog(gui, "New name");
            gui.getPlayerInfoPanel().setPlayerName(2, newName);
        }


    }

    private void swapActivePlayer() {
        if( activePlayer == 1)  activePlayer = 2;
        else activePlayer = 1;
        gui.getPlayerInfoPanel().setActivePlayer(activePlayer);
    }

    private void testServer() {
        // TODO: test a connection to the server.
    }


    private void setMovesDisplay() {
        // Set a boolean that acts as a switch when the user
        activateDisplay = !activateDisplay;
        System.out.println("Activate Display: " + activateDisplay);
        gui.getBoardView().setMovesDisplay(activateDisplay);
        gui.getBoardView().displayAvailableMoves(activateDisplay);

    }
}
