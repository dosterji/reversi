package plu.blue.reversi.client.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * The main menu bar.
 */
public class ReversiMenuBar extends JMenuBar implements ActionListener {

    /** The GameWindow */
    private GameWindow gui;

    /** Quit item */
    private JMenuItem quitMenuItem;

    /** New Game item */
    private JMenuItem newGameItem;

    /** New Computer item */
    private JMenuItem newCPUItem;

    // New Online Game item
    private JMenuItem newOnlineGameItem;

    // Menu items for saving and loading games
    private JMenuItem saveLocalGameItem;
    private JMenuItem loadLocalGameItem;
    private JMenuItem loadOnlineGameItem;
    private JMenuItem BoardColorSettingsItem;
    private JMenuItem displayMovesItem;
    private JMenuItem displayBestMove;
    private JMenuItem changePlayerNameItem;


    private boolean activateDisplay = false;
    private boolean activateBest = false;

    /**
     * Constructs the menu bar
     *
     * @param gui the main GameWindow
     */
    public ReversiMenuBar(GameWindow gui) {
        this.gui = gui;

        // Build the "Game" menu
        this.add( buildGameMenu() );

        // Add the developer menu.  This should be removed when
        // the game is released
        this.add(new DeveloperMenu(gui));
    }

    private JMenu buildGameMenu() {
        JMenu menu = new JMenu("Game");
        menu.getAccessibleContext().setAccessibleDescription(
                "New game");

        newGameItem = new JMenuItem("New Game");
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.META_MASK));
        newGameItem.getAccessibleContext().setAccessibleDescription(
                "Start a new game with a local player");
        newGameItem.addActionListener(this);
        menu.add(newGameItem);

        newCPUItem = new JMenuItem("New CPU Game");
        newCPUItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.META_MASK));
        newCPUItem.getAccessibleContext().setAccessibleDescription(
                "Start a new game against the computer");
        newCPUItem.addActionListener(this);
        menu.add(newCPUItem);

        displayMovesItem = new JMenuItem("Display Available Moves: ");
        displayMovesItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        displayMovesItem.addActionListener(this);
        menu.add(displayMovesItem);

        displayBestMove = new JMenuItem("Display Best Move");
        displayBestMove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        displayBestMove.addActionListener(this);
        menu.add(displayBestMove);

        newOnlineGameItem = new JMenuItem("New Online Game");
        newOnlineGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.META_MASK));
        newOnlineGameItem.getAccessibleContext().setAccessibleDescription("Start a new online game");
        newOnlineGameItem.addActionListener(this);
        menu.add(newOnlineGameItem);

        menu.addSeparator();

        saveLocalGameItem = new JMenuItem("Save Local Game");
        saveLocalGameItem.getAccessibleContext().setAccessibleDescription("Save the current local game.");
        saveLocalGameItem.addActionListener(this);
        menu.add(saveLocalGameItem);

        loadLocalGameItem = new JMenuItem("Load Local Game");
        loadLocalGameItem.getAccessibleContext().setAccessibleDescription("Load a saved local game.");
        loadLocalGameItem.addActionListener(this);
        menu.add(loadLocalGameItem);

        loadOnlineGameItem = new JMenuItem("Load Online Game");
        loadOnlineGameItem.getAccessibleContext().setAccessibleDescription("Load a saved online game.");
        loadOnlineGameItem.addActionListener(this);
        menu.add(loadOnlineGameItem);

        menu.addSeparator();

        quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.META_MASK));
        quitMenuItem.getAccessibleContext().setAccessibleDescription(
                "Exit Reversi.");
        quitMenuItem.addActionListener(this);
        menu.add(quitMenuItem);

        BoardColorSettingsItem = new JMenuItem("Board Color Settings");
        BoardColorSettingsItem.setAccelerator((KeyStroke.getKeyStroke( KeyEvent.VK_C, ActionEvent.META_MASK)));
        BoardColorSettingsItem.addActionListener(this);
        menu.add(BoardColorSettingsItem);

        changePlayerNameItem = new JMenuItem("Change Player Name");
        changePlayerNameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.META_MASK));
        changePlayerNameItem.addActionListener(this);
        menu.add(changePlayerNameItem);



        return menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == quitMenuItem) {
            System.exit(0);
        }
        if(e.getSource() == newGameItem) {
            gui.newGame();
        }
        if(e.getSource() == newCPUItem) {
            int diff = gui.chooseDiff();
            gui.newCPUGame(diff);
            gui.getBoardView().displayAvailableMoves(activateDisplay);
        }
        if (e.getSource() == newOnlineGameItem) {
            gui.newOnlineGame();
        }
        if (e.getSource() == saveLocalGameItem) {
            gui.saveLocalGame();
        }
        if (e.getSource() == loadLocalGameItem) {
            gui.loadLocalGame();
        }
        if (e.getSource() == loadOnlineGameItem) {
            gui.loadOnlineGame();
        }
        if (e.getSource() == BoardColorSettingsItem) {
            gui.BoardColorSettings();
        } else if( e.getSource() == displayMovesItem) {
            setMovesDisplay();
        } else if (e.getSource() == displayBestMove){
            setBestMoveDisplay();
        } else if(e.getSource() == changePlayerNameItem){
            changePlayerName();
        }
    }

    private void setBestMoveDisplay() {
        activateBest = !activateBest;
        System.out.println("Activate Display: " + activateBest);
        gui.getBoardView().setBestDisplay(activateBest);
        gui.getBoardView().displayBestMove(activateBest);

    }

    private void setMovesDisplay() {
        // Set a boolean that acts as a switch when the user
        activateDisplay = !activateDisplay;
        System.out.println("Activate Display: " + activateDisplay);
        gui.getBoardView().setMovesDisplay(activateDisplay);
        gui.getBoardView().displayAvailableMoves(activateDisplay);

    }
    /*
    public int chooseDiff(){
        int diff;
        Object[] possibleDiffs = { "1", "2", "3", "4", "5"};
        Object selectedDiff = JOptionPane.showInputDialog(null,
                "Select CPU Difficulty", "Input",
                JOptionPane.QUESTION_MESSAGE, null,
                possibleDiffs, possibleDiffs[0]);
        if(selectedDiff.equals("5"))
            diff = 0;
        else if(selectedDiff.equals("4"))
            diff = 2;
        else if(selectedDiff.equals("3"))
            diff = 4;
        else if(selectedDiff.equals("2"))
            diff = 6;
        else
            diff = 8;

        return diff;
    }
    */
    private void changePlayerName() {

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
}
