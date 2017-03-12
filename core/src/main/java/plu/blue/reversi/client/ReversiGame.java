package plu.blue.reversi.client;


public class ReversiGame
{
    /* The Players*/
    Player p1;
    Player p2;
    Player currentPlayer;
    /*The board */
    int[][] board;

    /**
     * Private inner class Player.
     * Holds the information about a player.
     */
    private class Player {
        //fields
        private String name;
        private int score;
        /* The color that this player is designed to use.
            The black player is designated by a integer value of 1.
            The white player is designated by a integer value of -1.
            Blank spaces on the board are designated by 0's.
         */
        private int color;

        public Player(String n, int c) {
            name = n;
            color = c;
        }

        public int getScore() {
            return score;
        }
        public void setScore(int s) {
            score = s;
        }
        public int getColor() {
            return color;
        }
        public String getName() {
            return name;
        }
    } //End Person

    //Reversie Constructors
    public ReversiGame() {
       new ReversiGame("Player 1", "Player2");
    }
    public ReversiGame(String n1, String n2) {
        p1 = new Player(n1, -1);
        p2 = new Player(n2, 1);
        currentPlayer = p1;

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

    /**
     * Make a move on the board as a player.
     * @param p the player making the move
     * @param rowLocation the location of the move they are trying to make
     * @param colLocation the location of the move they are trying to make
     * @return True if the move was successful, false otherwise
     */
    public boolean move( Player p, int rowLocation, int colLocation) {
        if( !isLegal(p, rowLocation, colLocation) )
            return false;
        //player changes board
        board[rowLocation][colLocation] = p.getColor();
        //set current player
        if( currentPlayer.equals(p1) )
            currentPlayer = p2;
        else
            currentPlayer = p1;
        return true;
    }

    /**
     * TODO: Implement an algorythm to check legality of a move.
     * @param p
     * @param rowLocation
     * @param colLocation
     * @return
     */
        private boolean isLegal( Player p, int rowLocation, int colLocation) {
            return true;
        }

}
