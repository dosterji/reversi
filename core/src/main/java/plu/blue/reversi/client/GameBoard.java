package plu.blue.reversi.client;

import java.util.ArrayList;

/**
 * Created by John on 3/15/2017.
 * This Class Represents the Game Board.
 *  Holds methods for determining if a method is legal and
 *  for altering the board.
 */
public class GameBoard {

    /*The board */
    private int[][] board;
    /*To Help Flip */
    private ArrayList<Coordinate> flips;

    public GameBoard(ArrayList<Move> moveHistory) {
        flips = new ArrayList<>();
        board = new int[8][8];

        for (Move m : moveHistory) {
            board[m.getCoordinate().getRowLocation()][m.getCoordinate().getColLocation()] = m.getPlayer().getColor();
        }
    }
    public GameBoard(GameBoard other) {
        flips = new ArrayList<>();
        board = new int[8][8];

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = other.board[i][j];
            }
        }
    }

    /**
     *
     * @param playerColor The color of the player making the move
     * @param row  The row in which the player wishes to place a tile
     * @param col The Column in which the player wishes to place a tile
     * @return Returns an arrayList containing the coordinates the player can flip to
     *          by placing a tile at location board[row][col];  Returns null if the move
     *          is not legal.
     */
    public ArrayList<Coordinate> isLegalMove( int playerColor, int row, int col) {
        flips.clear();
        //check to see if the space is empty
        if( board[row][col] != 0 )
            return null;
        //if not, attempt to play them game
        checkColumn( playerColor, row, col );
        checkRow( playerColor, row, col );
        checkLeftDiag( playerColor, row, col);
        checkRightDiag( playerColor, row, col);

        if( flips.isEmpty() ) {
            board[row][col] = 0;
            return null;
        }
        else {
            return flips;
        }
    }

    /**
     * Private Helper Method.
     * Checks the Columns for validity.
     * @param playerColor Black: -1, White: 1
     * @param row The row the player is placing a tile in
     * @param col THe column the player is placing a tile in
     */
    private void checkColumn( int playerColor, int row, int col ) {
        if( row != 0 ) {
            if (board[row - 1][col] != playerColor && board[row - 1][col] != 0) {
                for (int i = row - 2; i >= 0; i--) {
                    if (board[i][col] == playerColor) {
                        flips.add(new Coordinate(i, col));
                        break;
                    }
                    else if (board[i][col] == 0)
                        break;
                }
            }
        }
        if( row != 7) {
            if( board[row+1][col] != playerColor && board[row+1][col] !=0) {
                for( int i = row+2; i <= 7; i++) {
                    if( board[i][col] == playerColor ) {
                        flips.add(new Coordinate(i, col));
                        break;
                    }
                    else if( board[i][col] == 0)
                        break;
                }
            }
        }
    }

    /**
     * Private Helper Method.
     * Checks the rows for validity.
     * @param playerColor Black  -1, White: 1
     * @param row The row the player is placing a tile in
     * @param col the column the player is placing a tile in
     */
    private void checkRow(int playerColor, int row, int col ) {
        if( col != 0) {
            if (board[row][col - 1] != playerColor && board[row][col - 1] != 0) {
                for (int i = col - 2; i >= 0; i--) {
                    if (board[row][i] == playerColor) {
                        flips.add(new Coordinate(row, i));
                        break;
                    }
                    else if (board[row][i] == 0)
                        break;
                }
            }
        }
        if(col != 7) {
            if (board[row][col + 1] != playerColor && board[row][col + 1] != 0) {
                for (int i = col + 2; i <= 7; i++) {
                    if (board[row][i] == playerColor) {
                        flips.add(new Coordinate(row, i));
                        break;
                    }
                    else if (board[row][i] == 0)
                        break;
                }
            }
        }
    }//Standard Case

    /**
     * Checks the diagonal crossing from the top-left corner to bottom right
     * @param playerColor Black: -1, White: 1
     * @param row The row the player is placing a tile in
     * @param col The col the player is placing a tile in
     */
    private void checkLeftDiag( int playerColor, int row, int col) {
        if(row != 7 && col != 7) {
            if( board[row+1][col+1] != playerColor && board[row+1][col+1] != 0) {
                for(int i = 2; row + i <= 7 && col + i <= 7; i++) {
                    if( board[row+i][col+i] == playerColor ) {
                        flips.add(new Coordinate(row+i,col+i));
                        break;
                    }
                    if( board[row+i][col+i] == 0 ) {
                        break;
                    }
                }
            }
        }
        if(row != 0 && col != 0) {
            if( board[row-1][col- 1] != playerColor && board[row-1][col-1] != 0) {
                for(int i = 2; row - i >= 0 && col - i >= 0; i++) {
                    if( board[row-i][col-i] == playerColor ) {
                        flips.add(new Coordinate(row-i, col-i));
                        break;
                    }
                    if( board[row-i][col-i] == 0 ) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Checks the diagonal crossing from the top-right corner to the bottom left
     * @param playerColor Black: -1, White: 1
     * @param row The row the player is placing a tile in
     * @param col The column the player is placing a tile in
     */
    private void checkRightDiag( int playerColor, int row, int col) {
        if(row != 7 && col != 0) {
            if( board[row+1][col-1] != playerColor && board[row+1][col-1] != 0) {
                for(int i = 2; row + i <= 7 && col - i >= 0; i++) {
                    if( board[row+i][col-i] == playerColor ) {
                        flips.add(new Coordinate(row+i,col-i));
                        break;
                    }
                    if( board[row+i][col-i] == 0 ) {
                        break;
                    }
                }
            }
        }
        if(row != 0 && col != 7) {
            if( board[row-1][col+1] != playerColor && board[row-1][col+1] != 0) {
                for(int i =2; row - i >= 0 && col + i <= 7; i++) {
                    if( board[row-i][col+i] == playerColor ) {
                        flips.add(new Coordinate(row-i, col+i));
                        break;
                    }
                    if( board[row-i][col+i] == 0 ) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * This class updates the board by placing a tile of playerColor at the specified location
     * @param playerColor The current Player's Color
     * @param rowLocation the row Location
     * @param colLocation the column location
     */
    public void update(int playerColor, int rowLocation, int colLocation) {
        //player changes board
        board[rowLocation][colLocation] = playerColor;
        //System.out.println("Coordinates: ");
        for(int i=0; i<flips.size(); i++) {
            Coordinate current = flips.get(i);
            //System.out.print(current.toString() + " ");
            //update board[][]
            if(current.getRowLocation() == rowLocation) {
                if(current.getColLocation() > colLocation){
                    for(int j = colLocation; j <= current.getColLocation(); j++) {
                        board[rowLocation][j] = playerColor;
                    }
                }
                else{
                    for(int j = colLocation; j >= current.getColLocation(); j--) {
                        board[rowLocation][j] = playerColor;
                    }
                }
            } //end updating rows
            else if(current.getColLocation() == colLocation){
                if(current.getRowLocation() > rowLocation) {
                    for(int j = rowLocation; j <= current.getRowLocation(); j++) {
                        board[j][colLocation] = playerColor;
                    }
                }
                else{
                    for(int j = rowLocation; j >= current.getRowLocation(); j--) {
                        board[j][colLocation] = playerColor;
                    }
                }
            }// end updating columns
            else if(current.getColLocation() > colLocation && current.getRowLocation() > rowLocation) {
                for(int j = 0; colLocation+j <= current.getColLocation() && rowLocation+j <= current.getRowLocation(); j++) {
                    board[rowLocation+j][colLocation+j] = playerColor;
                }
            }
            else if(current.getColLocation() < colLocation && current.getRowLocation() < rowLocation) {
                for(int j =0; colLocation-j >= current.getColLocation() && rowLocation-j >= current.getRowLocation(); j++) {
                    board[rowLocation-j][colLocation-j] = playerColor;
                }
            }// end updating left Diagonal
            else if(current.getColLocation() > colLocation && current.getRowLocation() < rowLocation) {
                for(int j =0; colLocation+j <= current.getColLocation() && rowLocation-j >= current.getRowLocation(); j++) {
                    board[rowLocation-j][colLocation+j] = playerColor;
                }
            }// end updating right Diagonal
            else if(current.getColLocation() < colLocation && current.getRowLocation() > rowLocation) {
                for(int j =0; colLocation-j >= current.getColLocation() && rowLocation+j <= current.getRowLocation(); j++) {
                    board[rowLocation+j][colLocation-j] = playerColor;
                }
            }
        }//End Board Changing
    }

    /**
     * @return The Legal Moves for this player
     */
    public ArrayList<Coordinate> getLegalMoves(int playerColor) {
        int row = board.length;
        int col = row;
        ArrayList<Coordinate> moves = new ArrayList<Coordinate>();

        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                flips.clear();
                isLegalMove(playerColor, i, j);
                if(!flips.isEmpty())
                    moves.add(new Coordinate(i,j));
            }
        }
        return moves;
    }

    /**
     * Sets everything in the board to blank except for the inner pieces.
     */
    public void newGame() {
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8; j++) {
                board[i][j] = 0;
            }
        }

        board[3][3] = -1;
        board[3][4] = 1;
        board[4][3] = 1;
        board[4][4] = -1;
    }

    /**
     * Returns a string representation of the game board
     * @return A string representation of the board
     */
    public String toString() {
        String s = "";
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j<8; j++) {
                if(board[i][j]==1)
                    s += String.format("%3c", 'W');
                else if(board[i][j]==-1)
                    s += String.format("%3c", 'B');
                else
                    s += String.format("%3c", '-');
            }
            s += "\n";
        }
        return s;
    }
}
