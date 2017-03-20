package plu.blue.reversi.client;

import java.util.ArrayList;

/**
 * This class is the hub for our model.
 * It contains all of the information about the board
 * and players.
 */
public class ReversiGame
{
    /* The Players*/
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    /*The board */
    private GameBoard board;
    /* The game history */
    private GameHistory history;

    /**
     * Reversi Constructors
     */
    public ReversiGame() {
        this("Player1", "Player2");
    }

    public ReversiGame(String name1, String name2) {
        p1 = new Player(name1, -1); //Black
        p2 = new Player(name2, 1);  //White
        currentPlayer = p1;
        history = new GameHistory();
        board = new GameBoard(history.getMoveHistory());
        //Create the board
        newGame();

    }// End Reversi Constructors

    public Player getP1() { return p1; }
    public Player getP2() { return p2; }
    public GameBoard getBoard() { return board; }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public int getCurrentPlayerColor() {
        return currentPlayer.getColor();
    }

    public GameHistory getGameHistory() {
        return history;
    }
    /**
     * Make a move on the board as a player.
     * @param p the player making the move
     * @param rowLocation the location of the move they are trying to make
     * @param colLocation the location of the move they are trying to make
     * @return an arrayList containing all of the Coordinates to flip tiles from starting
     *          at rowLocation, colLocation
     */
    public ArrayList<Coordinate> move( Player p, int rowLocation, int colLocation) {
        int playerColor = p.getColor();
        ArrayList<Coordinate> flips = board.isLegalMove(playerColor, rowLocation, colLocation);

        if( flips == null)
            return null;
        board.update(playerColor, rowLocation, colLocation);
        history.addMove(new Move(new Coordinate(rowLocation, colLocation), p));
        System.out.println("\n" + toString());

        //set current player
        if( currentPlayer.getName().equals(p1.getName())) {
            currentPlayer = p2;
        }
        else {
            currentPlayer = p1;
        }
        return flips;
    }

    /**
     * Sets everything in the board to blank except for the inner pieces.
     */
    public void newGame() {
        board.newGame();
        history.newGame();
        currentPlayer = p1;
    }

    /**
     * Returns a string representation of the game board
     * @return A string representation of the board
     */
    public String toString() {
        return board.toString();
    }
}
