package plu.blue.reversi.client.firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.FirebaseDatabase;
import plu.blue.reversi.client.firebase.handlers.*;
import plu.blue.reversi.client.firebase.listeners.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FirebaseConnection {

    private static final FirebaseConnection instance = new FirebaseConnection();

    private final FirebaseDatabase database;

    private FirebaseChatHandler chatHandler;
    private FirebaseCurrentPlayerHandler currentPlayerHandler;
    private FirebaseMoveHistoryHandler moveHistoryHandler;
    private FirebasePlayersHandler playersHandler;

    private FirebaseConnection() {
        try {
            FileInputStream serviceAccount = new FileInputStream("serviceAccountCredentials.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://reversi-7f75f.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
            serviceAccount.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        database = FirebaseDatabase.getInstance();

        chatHandler = new FirebaseChatHandler();
        currentPlayerHandler = new FirebaseCurrentPlayerHandler();
        moveHistoryHandler = new FirebaseMoveHistoryHandler();
        playersHandler = new FirebasePlayersHandler();
    }

    public void createGame(String gameName, String password) {
        database.getReference("games").child(gameName).setValue(new Game(password));
    }

    public void addMove(String gameName, int row, int col, int player) {
        moveHistoryHandler.addMove(gameName, row, col, player);
    }

    public void addChatMessage(String gameName, String playerName, String message) {
        chatHandler.addMessage(gameName, playerName, message);
    }

    // +-----------------+
    // | Private Classes |
    // +-----------------+

    private class Game {

        public ArrayList<Move> moveHistory;
        public long currentPlayer;
        public String password;
        public Player playerOne;
        public Player playerTwo;

        public Game(String password) {
            moveHistory = new ArrayList<>();
            currentPlayer = 1;
            this.password = password;
            playerOne = new Player("Player 1", 2);
            playerTwo = new Player("Player 2", 2);

            moveHistory.add(new Move(3, 3, "playerOne"));
            moveHistory.add(new Move(4, 3, "playerTwo"));
            moveHistory.add(new Move(4, 4, "playerOne"));
            moveHistory.add(new Move(3, 4, "playerTwo"));
        }

    }

    private class Move {

        public int row, col;
        public String player;

        public Move(int row, int col, String player) {
            this.row = row;
            this.col = col;
            this.player = player;
        }

    }

    private class Player {

        public String name;
        public int score;

        public Player(String name, int score) {
            this.name = name;
            this.score = score;
        }

    }

    // +---------------+
    // | Add Listeners |
    // +---------------+

    public void addChatListener(String gameName, FirebaseChatListener lis) {
        chatHandler.addListener(gameName, lis);
    }

    public void addCurrentPlayerListener(String gameName, FirebaseCurrentPlayerListener lis) {
        currentPlayerHandler.addListener(gameName, lis);
    }

    public void addMoveHistoryListener(String gameName, FirebaseMoveHistoryListener lis) {
        moveHistoryHandler.addListener(gameName, lis);
    }

    public void addPlayersListener(String gameName, FirebasePlayersListener lis) {
        playersHandler.addListener(gameName, lis);
    }

    // +---------+
    // | Getters |
    // +---------+

    public static FirebaseConnection getInstance() {
        return instance;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

}
