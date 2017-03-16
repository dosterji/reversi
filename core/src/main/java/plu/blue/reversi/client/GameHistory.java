package plu.blue.reversi.client;

import java.util.ArrayList;

/**
 * Class that represents the history of a Reversi game instance
 * @author Adam Grieger
 */
public class GameHistory {

    // Field declarations
    private ArrayList<Coordinate> moveHistory;
    private Player player1, player2;

    /**
     * GameHistory constructor
     * @param p1 the first Player
     * @param p2 the second Player
     */
    public GameHistory(Player p1, Player p2) {
        moveHistory = new ArrayList<>();
        player1 = p1;
        player2 = p2;
    }

    /**
     * Adds a move to the GameHistory
     * @param c the coordinate of the move
     * @return true if the move was successfully added, false if otherwise
     */
    public boolean addMove(Coordinate c) {
        return moveHistory.add(c);
    }

    // +---------+
    // | Getters |
    // +---------+

    public ArrayList<Coordinate> getMoveHistory() {
        return moveHistory;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    // +---------+
    // | Setters |
    // +---------+

    public void setPlayer1(Player p) {
        player1 = p;
    }

    public void setPlayer2(Player p) {
        player2 = p;
    }

}
