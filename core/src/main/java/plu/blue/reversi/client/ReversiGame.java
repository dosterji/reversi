package plu.blue.reversi.client;


import java.util.ArrayList;

public class ReversiGame
{
    /* The Players*/
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    /*The board */
    private int[][] board;
    /*To Help Flip */
    private ArrayList<Coordinate> flips;


    /**
     * Reversi Contructors
     */
    public ReversiGame() {
        this("Player1", "Player2");
    }
    public ReversiGame(String name1, String name2) {
        p1 = new Player(name1, -1);
        p2 = new Player(name2, 1);
        currentPlayer = p1;
        flips = new ArrayList<Coordinate>();

        //Create the board
        board = new int[8][8];
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8; j++) {
                board[i][j] = 0;
            }
        }

        board[3][3] = -1;
        board[3][4] = 1;
        board[4][3] = 1;
        board[4][4] = -1;
    }// End Reversi Constructors

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public int getCurrentPlayerColor() {
        return currentPlayer.getColor();
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
        flips.clear();
        int playerColor = p.getColor();

        if( !isLegal(playerColor, rowLocation, colLocation) )
            return null;

        //player changes board
        board[rowLocation][colLocation] = playerColor;
        for(int i=0; i<flips.size(); i++) {
            Coordinate current = flips.get(i);
            System.out.println(current.toString());
            //update board[][]
            if(current.getRowLocation() == rowLocation) {
                if(current.getColLocation() > colLocation){
                    System.out.println("Updating East");
                    for(int j = colLocation; j <= current.getColLocation(); j++) {
                        board[rowLocation][j] = playerColor;
                    }
                }
                else{
                    System.out.println("Updating West");
                    for(int j = colLocation; j >= current.getColLocation(); j--) {
                        board[rowLocation][j] = playerColor;
                    }
                }
            } //end updating rows
            else if(current.getColLocation() == colLocation){
                if(current.getRowLocation() > rowLocation) {
                    System.out.println("Updating South");
                    for(int j = rowLocation; j <= current.getRowLocation(); j++) {
                        board[j][colLocation] = playerColor;
                    }
                }
                else{
                    System.out.println("Updating North");
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
        System.out.println(toString());

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
     *
     * This method determines if a Move is legal, and then alters the board accordingly.
     * Does a quick search of board[][] to see if a move is legal or not.
     * @param playerColor Black: -1, White: 1
     * @param row The row the player is placing a tile in
     * @param col The column the player is placing a tile in
     * @return True is the move is valid, false otherwise
     */
    private boolean isLegal( int playerColor, int row, int col) {

        //check to see if the space is empty
        if( board[row][col] != 0 )
            return false;

        //if not, attempt to play them game
        board[row][col] = playerColor;
        checkColumn( playerColor, row, col );
        checkRow( playerColor, row, col );
        checkLeftDiag( playerColor, row, col);
        checkRightDiag( playerColor, row, col);

        if( flips.isEmpty() ) {
            board[row][col] = 0;
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Private Helper Method.
     * Checks the Column.
     * @param playerColor Balck: -1, White: 1
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
