package plu.blue.reversi.client;

import java.io.Serializable;
import plu.blue.reversi.client.gui.GameWindow;

import javax.swing.*;
import java.util.ArrayList;

/**
 * This class is the hub for our model.
 * It contains all of the information about the board
 * and players.
 */
public class ReversiGame implements Serializable {
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
        this("PLAYER 1", "PLAYER 2");
    }
    public ReversiGame(String name1, String name2) {
        p1 = new Player(name1, -1); //Black
        p2 = new Player(name2, 1);  //White
        currentPlayer = p1;
        history = new GameHistory();
        board = new GameBoard(history.getMoveHistory());
        //Create the board
        newGame();

    }
    public ReversiGame(ReversiGame other) {
        this.p1 = new Player(other.getP1());
        this.p2 = new Player(other.getP2());
        this.currentPlayer = new Player(other.getCurrentPlayer());
        this.history = new GameHistory();  //This field isn't important for the copy contructor
        this.board = new GameBoard(other.getBoard());
    } // End Reversi Constructors

    public Player getP1() { return p1; }
    public Player getP2() { return p2; }
    public void setP2(CPU comp) {p2 = comp; }
    public void setP2(Player p) {p2 = p; }
    public GameBoard getBoard() { return board; }
    public void setBoard(GameBoard board) { this.board = board;}
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
     * Swaps currentPlayer when the current turn is finished.
     */
    public int changeCurrentPlayer() {
        if( currentPlayer.getName().equals(p1.getName())) {
            currentPlayer = p2;
            return 2;
        }
        else {
            currentPlayer = p1;
            return 1;
        }
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

        changeCurrentPlayer();

        p1.setScore(calculateScore(toString(), 1));
        p2.setScore(calculateScore(toString(), 2));

        return flips;
    }

    /**
     * A method for updating the score after a move has been made.
     * @param scoreBoard A string representation of the board
     * @param playerNumber 1 or 2
     * @return returns the value for the score of the player after the current move.
     */
    public int calculateScore(String scoreBoard, int playerNumber) {
        char pieceColor = '-';
        int score = 0;
        int index = 0;

        if(playerNumber == 1)
            pieceColor = 'B';
        else pieceColor = 'W';

        //System.out.println("Piece Color is: "  + pieceColor);

        while( index < scoreBoard.length() ) {
            if (scoreBoard.charAt(index) == pieceColor) {
                //System.out.println("Character: " + scoreBoard.charAt(index));
                score = score + 1;
            }
            index = index + 1;
            //System.out.println("P-" + playerNumber +  " S-" + score + " I-" + index );
        }
        return score;
    }

    public ArrayList<Coordinate> makeCPUMove() {
        Coordinate m =((CPU) p2).move(this);
        ArrayList<Coordinate> a = new ArrayList<Coordinate>();
        ArrayList<Coordinate> moves = move(currentPlayer, m.getRowLocation(), m.getColLocation());
        a.add(m);
        for(int i = 0; i < moves.size(); i++ )
            a.add(moves.get(i));

        return a;
    }

    /**
     * Helper method to avoid cluster in BoardViewClass.
     * It helps with the smoother flip animation.
     * @return A coordinate adjusted
     */
    public Coordinate adjustCoordForFlipping(int cellRow, int cellCol, int row, int col) {
        if( cellRow < row)
            row -= 1;
        if (cellRow > row)
            row += 1;

        if(cellCol < col)
            col -= 1;
        if(cellCol > col)
            col += 1;

        return new Coordinate(row,col);
    }
    /**
     * @return the legal coordinates on te board in which the current player may place a piece
     */
    public ArrayList<Coordinate> getCurrentPlayerMoves() {
        return board.getLegalMoves(currentPlayer.getColor());
    }

    /**
     * Sets everything in the board to blank except for the inner pieces.
     */
    public void newGame() {
        board.newGame();
        history.newGame();
        currentPlayer = p1;
        p1.setScore(2);
        p2.setScore(2);
    }

    /**
     * Method for determining if the game is over.
     * @param gui The GameWindow
     */
    public void isEndGame(GameWindow gui) {
        //Determine if the new player has any moves
        ArrayList<Coordinate> moves = getCurrentPlayerMoves();
        System.out.println("Number of Possible Next Moves: " + moves.size());
        if(moves.isEmpty()) {
            int p = changeCurrentPlayer();
            gui.getPlayerInfoPanel().setActivePlayer(p);

            moves = getCurrentPlayerMoves();
            if (moves.isEmpty()) { //If Neither Player has any moves, game is over.
                int p1s = getP1().getScore();
                int p2s = getP2().getScore();
                if (p1s > p2s)
                    JOptionPane.showMessageDialog(gui, "Black Player Wins");
                else if (p1s < p2s)
                    JOptionPane.showMessageDialog(gui, "White Player Wins");
                else
                    JOptionPane.showMessageDialog(gui, "Tie Game");
            }
        }
    }

    /**
     * Returns a string representation of the game board
     * @return A string representation of the board
     */
    public String toString() {
        return board.toString();
    }
}
