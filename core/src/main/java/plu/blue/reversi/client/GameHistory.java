package plu.blue.reversi.client;

import java.util.ArrayList;

/**
 * Class that represents the history of a Reversi game instance
 * @author Adam Grieger
 */
public class GameHistory {

    // Field declarations
    private ArrayList<Move> moveHistory;

    /**
     * GameHistory constructor
     */
    public GameHistory() {
        moveHistory = new ArrayList<>();
    }

    /**
     * Adds a move to the GameHistory
     * @param m the move
     * @return true if the move was successfully added, false if otherwise
     */
    public boolean addMove(Move m) {
        return moveHistory.add(m);
    }

    // +---------+
    // | Getters |
    // +---------+

    public ArrayList<Move> getMoveHistory() {
        return moveHistory;
    }

}
