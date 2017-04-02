package plu.blue.reversi.client.gui;

import plu.blue.reversi.client.ReversiGame;

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

        quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.META_MASK));
        quitMenuItem.getAccessibleContext().setAccessibleDescription(
                "Exit Reversi.");
        quitMenuItem.addActionListener(this);
        menu.add(quitMenuItem);

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
        }
    }
}
