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
     * Returns a string representation of the game board
     * @return A string representation of the board
     */
    public String toString() {
        return board.toString();
    }
}
