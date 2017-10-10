package plu.blue.reversi.client.gui;

import plu.blue.reversi.client.model.CPU;
import plu.blue.reversi.client.model.LocalStorage;
import plu.blue.reversi.client.model.Player;
import plu.blue.reversi.client.model.ReversiGame;
import plu.blue.reversi.client.firebase.FirebaseConnection;

import javax.swing.*;
import java.awt.*;

/**
 * The main game window, contains all of the UI.
 */
public class GameWindow extends JFrame {

    /** The panel containing the board */
    private BoardView boardView;

    /** The panel containing the player's names and scores */
    private PlayerInfoPanel playerInfoPanel;

    /** The panel containing the game history */
    private GameHistoryPanel historyPanel;

    /** The model for the game */
    private ReversiGame game;

    // Local storage for the GameWindow instance
    private LocalStorage storage;

    //Player Chat area
    private ChatPanel chat;

    // Connection for accessing Firebase
    private FirebaseConnection firebase;

    /**
     * Constructs a new main window for the game.
     */
    public GameWindow() {
        game = new ReversiGame();
        setTitle("Reversi");
        init();
        newGame();
    }

    /**
     * Constructs a new main window for this game from an existing game.
     * @param rg the ReversiGame to load
     */
    public GameWindow(ReversiGame rg, String saveName) {
        game = rg;
        setTitle("Reversi - " + saveName);
        init();
    }

    /**
     * GUI setup that is common for new games and loaded games
     */
    private void init() {
        //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //<--Is this what we want?

        storage = new LocalStorage();
        firebase = new FirebaseConnection("serviceAccountCredentials.json");

        // Add the menu bar
        this.setJMenuBar(new ReversiMenuBar(this));

        // The panel that holds the BoardView and the PlayerInfoPanel
        JPanel boardPanel = new JPanel(new BorderLayout(0, 0));

        // Other panels
        playerInfoPanel = new PlayerInfoPanel();
        boardView = new BoardView(8, game, playerInfoPanel, this);

        // This panel will preserve the aspect ratio of the component within it
        JPanel preserveAspectPanel = new JPanel(new PreserveAspectRatioLayout());

        // The board and edges
        JPanel boardAndEdges = new JPanel(new BorderLayout());
        boardAndEdges.add(BoardEdges.createTopPanel(8), BorderLayout.NORTH);
        boardAndEdges.add(BoardEdges.createLeftPanel(8), BorderLayout.WEST);
        boardAndEdges.add(boardView, BorderLayout.CENTER);
        boardAndEdges.setBorder(BorderFactory.createMatteBorder(0, 0, BoardEdges.EDGE_HEIGHT,
                BoardEdges.EDGE_WIDTH, BoardEdges.BACKGROUND_COLOR));

        preserveAspectPanel.add(boardAndEdges);

        boardPanel.add(preserveAspectPanel, BorderLayout.CENTER);
        boardPanel.add(playerInfoPanel, BorderLayout.NORTH);

        historyPanel = new GameHistoryPanel(game.getGameHistory());

        // History panel goes in the EAST
        this.add(historyPanel, BorderLayout.EAST);

        //Chat panel goes into the SOUTH
        chat = new ChatPanel();
        this.add(chat, BorderLayout.SOUTH);
        // The board panel goes in the CENTER
        this.add(boardPanel, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Returns the PlayerInfoPanel
     * @return the PlayerInfoPanel
     */
    public PlayerInfoPanel getPlayerInfoPanel() { return playerInfoPanel; }

    /**
     * @return the BoardView
     */
    public BoardView getBoardView() { return boardView; }

    /**
     * Starts a new Reversi game
     */
    public void newGame() {
        game.newGame();
        historyPanel.newGame();
        boardView.newGame();
        game.setP2(new Player("PLAYER 2", 1));
        playerInfoPanel.setPlayerName(1, game.getP1().getName());
        playerInfoPanel.setPlayerName(2, game.getP2().getName());
    }
    public void newCPUGame(int difficulty) {
        int depth = difficulty - 8;
        System.out.println("CPU Search Depth: " + depth);
        game.newGame();
        historyPanel.newGame();
        boardView.newGame();
        game.setP2(new CPU(difficulty));
        playerInfoPanel.setPlayerName(1, game.getP1().getName());
        playerInfoPanel.setPlayerName(2, game.getP2().getName());
    }

    /**
     * Saves a ReversiGame to local storage
     */
    public void saveLocalGame() {
        String saveName = JOptionPane.showInputDialog(
                this, "Enter a name for this save file:", "Save Local Game", JOptionPane.PLAIN_MESSAGE
        );
        storage.saveGame(saveName, game);
    }

    /**
     * Loads a ReversiGame from local storage
     */
    public void loadLocalGame() {
        Object[] saveGameNames = storage.getSavedGameNames();
        String saveName = (String) JOptionPane.showInputDialog(
                this, "Select a save game:", "Load Local Game", JOptionPane.PLAIN_MESSAGE,
                null, saveGameNames, saveGameNames[0]
        );
        ReversiGame savedGame = storage.getSavedGame(saveName);
        new GameWindow(savedGame, saveName);
    }

    public void BoardColorSettings() {
        new BoardColorGUI(game, boardView);
    }
}