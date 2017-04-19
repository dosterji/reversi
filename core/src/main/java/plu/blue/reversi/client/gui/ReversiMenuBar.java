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

    // Menu items for saving and loading games
    private JMenuItem saveLocalGameItem;
    private JMenuItem loadLocalGameItem;
    private JMenuItem loadOnlineGameItem;
    private JMenuItem BoardColorSettingsItem;
    private JMenuItem displayMovesItem;


    private boolean activateDisplay = false;

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

        JMenuItem menuItem = new JMenuItem("New Online Game");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.META_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Start a new online game and invite someone to play.");
        menu.add(menuItem);

        menuItem = new JMenuItem("Join Online Game");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_J, ActionEvent.META_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Join an existing online game.");
        menu.add(menuItem);

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
            gui.newCPUGame();
            gui.getBoardView().displayAvailableMoves(activateDisplay);
        }
        if (e.getSource() == saveLocalGameItem) {
            gui.saveLocalGame();
        }
        if (e.getSource() == loadLocalGameItem) {
            gui.loadLocalGame();
        }
        if (e.getSource() == BoardColorSettingsItem) {
            gui.BoardColorSettings();
        } else if( e.getSource() == displayMovesItem) {
            setMovesDisplay();
        }
    }

    private void setMovesDisplay() {
        // Set a boolean that acts as a switch when the user
        activateDisplay = !activateDisplay;
        System.out.println("Activate Display: " + activateDisplay);
        gui.getBoardView().setMovesDisplay(activateDisplay);
        gui.getBoardView().displayAvailableMoves(activateDisplay);

    }
}
