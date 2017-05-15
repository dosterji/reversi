package plu.blue.reversi.client.gui;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import plu.blue.reversi.client.model.CPU;
import plu.blue.reversi.client.model.LocalStorage;
import plu.blue.reversi.client.model.Player;
import plu.blue.reversi.client.model.ReversiGame;
import plu.blue.reversi.client.firebase.FirebaseConnection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
     * Constructs a new main window for the game from an online game.
     * Copied and pasted from other constructor for testing purposes.
     * @param onlineGameName the name of the online game to load
     * @param player which player the game instance controls
     *               (1 if player one, 2 if player two)
     */
    public GameWindow(String onlineGameName, int player) {
        game = new ReversiGame();
        setTitle("Reversi - " + onlineGameName);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        firebase = FirebaseConnection.getInstance();

        this.setJMenuBar(new ReversiMenuBar(this));

        JPanel boardPanel = new JPanel(new BorderLayout(0, 0));

        playerInfoPanel = new PlayerInfoPanel();
        firebase.addCurrentPlayerListener(onlineGameName, playerInfoPanel);
        firebase.addPlayersListener(onlineGameName, playerInfoPanel);
        boardView = new BoardView(8, game, playerInfoPanel, this);

        JPanel preserveAspectPanel = new JPanel(new PreserveAspectRatioLayout());

        JPanel boardAndEdges = new JPanel(new BorderLayout());
        boardAndEdges.add(BoardEdges.createTopPanel(8), BorderLayout.NORTH);
        boardAndEdges.add(BoardEdges.createLeftPanel(8), BorderLayout.WEST);
        boardAndEdges.add(boardView, BorderLayout.CENTER);
        boardAndEdges.setBorder(BorderFactory.createMatteBorder(
                0, 0, BoardEdges.EDGE_HEIGHT, BoardEdges.EDGE_WIDTH, BoardEdges.BACKGROUND_COLOR
        ));

        preserveAspectPanel.add(boardAndEdges);

        boardPanel.add(preserveAspectPanel, BorderLayout.CENTER);
        boardPanel.add(playerInfoPanel, BorderLayout.NORTH);

        historyPanel = new GameHistoryPanel(game.getGameHistory());

        this.add(historyPanel, BorderLayout.EAST);

        this.add(boardPanel, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    /**
     * GUI setup that is common for new games and loaded games
     */
    private void init() {
        //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //<--Is this what we want?

        storage = new LocalStorage();
        firebase = FirebaseConnection.getInstance();

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

    public void newOnlineGame() {
        JTextField onlineGameName = new JTextField();
        JTextField onlineGamePassword = new JPasswordField();
        Object[] message = {
                "Game Name:", onlineGameName,
                "Game Password:", onlineGamePassword
        };

        int option = JOptionPane.showConfirmDialog(null, message, "New Online Game", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            firebase.createGame(onlineGameName.getText(), onlineGamePassword.getText());
        }
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
                null, saveGameNames, null
        );
        ReversiGame savedGame = storage.getSavedGame(saveName);
        new GameWindow(savedGame, saveName);
    }

    public void loadOnlineGame() {
        DatabaseReference ref = firebase.getDatabase().getReference("games");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> gameNames = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    gameNames.add(ds.getKey());
                }
                JComboBox onlineGameNames = new JComboBox<>(gameNames.toArray());
                JTextField onlineGamePassword = new JPasswordField();
                JRadioButton playerOneButton = new JRadioButton("Player One");
                playerOneButton.setActionCommand("Player One");
                JRadioButton playerTwoButton = new JRadioButton("Player Two");
                playerTwoButton.setActionCommand("Player Two");
                ButtonGroup group = new ButtonGroup();
                group.add(playerOneButton);
                group.add(playerTwoButton);
                Object[] message = {
                        "Game Name:", onlineGameNames,
                        "Game Password:", onlineGamePassword,
                        playerOneButton, playerTwoButton
                };
                int option = JOptionPane.showConfirmDialog(null, message, "Join Online Game", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String gameName = onlineGameNames.getSelectedItem().toString();
                    String password = onlineGamePassword.getText();
                    int player = group.getSelection().getActionCommand().equals("Player One") ? 1 : 2;
                    DatabaseReference gameRef = firebase.getDatabase().getReference("games/" + gameName + "/password");
                    gameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue(String.class).equals(password)) {
                                new GameWindow(gameName, player);
                            } else {
                                JOptionPane.showMessageDialog(null, "The password you entered is incorrect.");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println(databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public void BoardColorSettings() {
        new BoardColorGUI(game, boardView);
    }
}