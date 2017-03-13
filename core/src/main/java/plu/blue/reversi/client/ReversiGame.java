package plu.blue.reversi.client;


import java.util.ArrayList;

public class ReversiGame
{
    /* The Players*/
    Player p1;
    Player p2;
    Player currentPlayer;
    /*The board */
    int[][] board;
    /*To Help Flip */
    ArrayList<Coordinate> flips;


    /**
     * Reversi Contructors
     */
    public ReversiGame() {this("Player1", "Player2"); }
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
     * @return True if the move was successful, false otherwise
     */
    public ArrayList<Coordinate> move( Player p, int rowLocation, int colLocation) {
        System.out.println(this.toString());
        flips.clear();
        int playerColor = p.getColor();

        if( !isLegal(playerColor, rowLocation, colLocation) )
            return null;
        //player changes board
        board[rowLocation][colLocation] = playerColor;
        for(int i=0; i<flips.size(); i++) {
            Coordinate current = flips.get(i);
            //Is row constant?
            if(current.getRowLocation() == rowLocation) {
                if(current.getColLocation() > colLocation){
                    for(int j = colLocation; j <= current.getColLocation(); j++) {
                        board[rowLocation][j] = playerColor;
                    }
                }
                else{
                    for(int j = current.getColLocation(); j < colLocation; j++) {
                        board[rowLocation][j] = playerColor;
                    }
                }
            }
            else{
                if(current.getRowLocation() > rowLocation) {
                    for(int j = rowLocation; j <= current.getRowLocation(); j++) {
                        board[j][colLocation] = playerColor;
                    }
                }
                else{
                    for(int j = current.getColLocation(); j <= rowLocation; j++) {
                        board[j][colLocation] = playerColor;
                    }
                }
            }
        }//End Board Changing
        System.out.println(this.toString());

        //set current player
        if( currentPlayer.getName().equals(p1.getName()))
            currentPlayer = p2;
        else
            currentPlayer = p1;
        return flips;
    }

    /**
     *
     * This method determines if a Move is legal, and then alters the board accordingly.
     * Does a quick search of board[][] to see if a move is legal or not.
     * @param playerColor
     * @param row
     * @param col
     * @return
     */
    public boolean isLegal( int playerColor, int row, int col) {

        //check to see if the space is empty
        if( board[row][col] != 0 )
            return false;

        //if not, attempt to play them game
        board[row][col] = playerColor;
        checkColumn( playerColor, row, col );
        checkRow( playerColor, row, col );

        System.out.println("\n" + !flips.isEmpty() + "\n");
        if( flips.isEmpty() ) {
            board[row][col] = 0;
            return false;
        }
        else {
            for(int i=0; i < flips.size(); i++) {
                Coordinate current = flips.get(i);
            }
            return true;
        }
    }

    /**
     * Private Helper Method.
     * Checks the Column.
     * @param playerColor
     * @param row
     * @param col
     */
    private void checkColumn( int playerColor, int row, int col ) {
        System.out.println("checkColumn");
        if( row == 7 ) {
            if(board[row-1][col] != playerColor && board[row-1][col] != 0) {
                for(int i = row-2; i>=0; i--) {
                    if( board[i][col] == playerColor )
                        flips.add( new Coordinate(i, col));
                    else if( board[i][col] == 0 )
                        break;
                }
            }
        }//Sepcial Case (edge)
        else if( row == 0 ) {
            if(board[row+1][col] != playerColor && board[row+1][col] != 0) {
                for(int i = row+2; i<=7; i++) {
                    if( board[i][col] == playerColor )
                        flips.add( new Coordinate(i, col));
                    else if( board[i][col]== 0 )
                        break;
                }
            }
        }//Special Case (edge)
        else{
            if(board[row-1][col] != playerColor && board[row-1][col] != 0) {
                for( int i = row-2; i >= 0; i--) {
                    if( board[i][col] == playerColor )
                        flips.add(new Coordinate(i, col));
                    else if( board[i][col] == 0)
                        break;
                }
            }
            if( board[row+1][col] != playerColor && board[row+1][col] !=0) {
                for( int i = row+2; i <= 7; i++) {
                    if( board[i][col] == playerColor )
                        flips.add(new Coordinate(i, col));
                    else if( board[i][col] == 0)
                        break;
                }
            }
        }//Standard Case
    }

    /**
     * Private Helper Method.
     * Checks the rows for validity.
     * @param playerColor
     * @param row
     */
    private void checkRow(int playerColor, int row, int col ) {
        System.out.println("checkRow");
        if( col == 7 ) {
            if(board[row][col-1] != playerColor && board[row][col-1] != 0) {
                for(int i = col-2; i>=0; i--) {
                    if( board[row][i] == playerColor )
                        flips.add( new Coordinate(row, i));
                    else if( board[row][i] == 0 )
                        break;
                }
            }
        }//Sepcial Case (edge)
        else if( col == 0 ) {
            if(board[row][col+1] != playerColor && board[row][col+1] != 0) {
                for(int i = col+2; i<=7; i++) {
                    if( board[row][i] == playerColor )
                        flips.add( new Coordinate(row, i));
                    else if( board[row][i]== 0 )
                        break;
                }
            }
        }//Special Case (edge)
        else{
            if(board[row][col-1] != playerColor && board[row][col-1] != 0) {
                for( int i = col-2; i >= 0; i--) {
                    if( board[row][i] == playerColor )
                        flips.add(new Coordinate(row, i));
                    else if( board[row][i] == 0)
                        break;
                }
            }
            if( board[row][col+1] != playerColor && board[row][col+1] !=0) {
                for( int i = col+2; i <= 7; i++) {
                    if( board[row][i] == playerColor )
                        flips.add(new Coordinate(row, i));
                    else if( board[row][i] == 0)
                        break;
                }
            }
        }//Standard Case
    }

    /**
     * Returns a string representation of the game board
     * @return
     */
    public String toString() {

        StringBuilder s = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j<7; j++) {
                s.append(board[i][j] + " ");
                if(j == 6)
                    s.append(board[i][j] + "\n");
            }
        }
        return s.toString();
    }
}
