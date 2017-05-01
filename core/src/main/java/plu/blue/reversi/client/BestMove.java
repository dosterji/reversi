package plu.blue.reversi.client;

import java.util.ArrayList;

/**
 * Find the Best Move when given an ArrayList of Coordinates
 * It does this based upon the formula:
 *
 *          (Player2Score - player1Score)
 * value =   ----------------------------
 *          (PLayer2Score + Player1Score)
 *
 * The more negative the value for a move, the better the move
 * is for player 1.  The more positive, the better the move is for
 * player 2.
 *
 * Created by John on 4/2/2017.
 */
public class BestMove {

    ////////////////////////
    // Private Inner Class//
    ////////////////////////
    /**
     * This class finds the "Value" for each move by making the
     * move on a copy of the ReversiGame and then calculating value
     */
    private class Node {
        private double value;
        private Coordinate coord;

        public Node(Coordinate c, ReversiGame game) {
            coord = c;
            int color = game.getCurrentPlayerColor();
            if( c.isCorner()) {
                value = assignCornerValue(color);
                //System.out.println("Corner: " + value);
            }
            else {
                game.move(game.getCurrentPlayer(), c.getRowLocation(), c.getColLocation());

                double top = game.getP2().getScore() - game.getP1().getScore();
                double bot = game.getP2().getScore() + game.getP1().getScore();
                value = top / bot;
            }
        }
        public double getValue() {return value;}
        public Coordinate getCoord() {return coord;}
    }

    /////////////////////////
    //   Best Move Class   //
    /////////////////////////
    private double alpha = 1.0;
    private double beta = -1.0;
    private Coordinate best;
    private double val;
    /**
     * This is basically just a constructor.  It sets the field "best"
     * to the coordinate for the best value for the current Player.
     */
    public BestMove(ReversiGame g, ArrayList<Coordinate> possibleMoves) {
        double playerColor = g.getCurrentPlayerColor();
        Node n, target;
        target = new Node(possibleMoves.get(0), new ReversiGame(g));

        for(int i = 1; i < possibleMoves.size(); i ++) {
           n = new Node(possibleMoves.get(i), new ReversiGame(g));
           if(playerColor == beta) {
                if( n.getValue() < target.getValue())
                    target = n;
           }
           if(playerColor == alpha) {
               if( n.getValue() > target.getValue() )
                   target = n;
           }
        }
        best = target.getCoord();
        val = target.getValue();
    }

    /**
     * This constructor basically allows a user to access the node
     * class for a single coordinate
     * @param c The Coordinate
     * @param game
     */
    public BestMove (Coordinate c , ReversiGame game) {
        ReversiGame copy = new ReversiGame(game);
        Node n = new Node(c, copy);
        val = n.getValue();
        best = n.getCoord();
    }
    ///////////
    //GETTERS//
    ///////////
    public Coordinate getBest() {return best;}
    public double getValue() {return val;}

    /**
     * assigns a value to corner values
     * @param playerColor the player making the move
     * @return the value
     */
    public double assignCornerValue(int playerColor) {
        if(playerColor == 1)
            return 0.999;
        else
            return -0.999;
    }

} //End Class
