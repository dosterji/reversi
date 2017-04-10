package plu.blue.reversi.client;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that represents the history of a Reversi game instance
 * @author Adam Grieger
 */
public class GameHistory implements Serializable {

    // Field declarations
    private ArrayList<Move> moveHistory;

    // Transient here means that tableRef won't be serialized
    private transient JTable tableRef;

    /**
     * GameHistory constructor
     */
    public GameHistory() {
        moveHistory = new ArrayList<>();
        Player p1 = new Player("Setup", -1);
        Player p2 = new Player("Setup", 1);

        moveHistory.add(new Move(new Coordinate(3, 3), p1));
        moveHistory.add(new Move(new Coordinate(3, 4), p2));
        moveHistory.add(new Move(new Coordinate(4, 4), p1));
        moveHistory.add(new Move(new Coordinate(4, 3), p2));
    }

    /**
     * Adds a move to the GameHistory
     * @param m the move
     * @return true if the move was successfully added, false if otherwise
     */
    public boolean addMove(Move m) {
        boolean success = moveHistory.add(m);

        if (success && (tableRef != null)) {
            tableRef.tableChanged(null);
        }

        return success;
    }

    /**
     * Adds the GameHistoryPanel's JTable so this class can refresh the history table
     * @param ref the GameHistoryPanel's JTable reference
     */
    public void addTableReference(JTable ref) {
        tableRef = ref;
    }

    /**
     * Sets the move history to its starting values
     */
    public void newGame() {
        moveHistory.subList(4, moveHistory.size()).clear();
    }

    // +---------+
    // | Getters |
    // +---------+

    public ArrayList<Move> getMoveHistory() {
        return moveHistory;
    }

}
