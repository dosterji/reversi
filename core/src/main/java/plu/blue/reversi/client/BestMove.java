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
    private double value;
    private Coordinate coord;


    public BestMove (Coordinate c , ReversiGame game) {
        //private Coordinate coord;
        coord = c;

        game.move(game.getCurrentPlayer(), c.getRowLocation(), c.getColLocation());

        double top = game.getP2().getScore() - game.getP1().getScore();
        double bot = game.getP2().getScore() + game.getP1().getScore();

        value = top / bot;
        System.out.println("VALUE: " + value);
    }
    public double getValue() {return value;}
    public Coordinate getCoord() {return coord;}



    /**
     * This class finds the "Value" for each move by making the
     * move on a copy of the ReversiGame and then calculating value
     */
    private class Node {
        private double value;
        private Coordinate coord;

        public Node(Coordinate c, ReversiGame game) {
            coord = c;
            game.move(game.getCurrentPlayer(), c.getRowLocation(),c.getColLocation());

            double top = game.getP2().getScore() - game.getP1().getScore();
            double bot = game.getP2().getScore() + game.getP1().getScore();
            value = top/bot;
        }
        public double getValue() {return value;}
        public Coordinate getCoord() {return coord;}
    }

    /////////////////////////
    //   Best Move Class   //
    /////////////////////////

    /**
     * This is basically just a constructor.  It sets the field "best"
     * to the coordinate for the best value for the current Player.
    */

    private double alpha = 1.0;
    private double beta = -1.0;
    private Coordinate best;
    private double val;

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
     * Some Getters
     * @return the fields
     */

    public Coordinate getBest() {return best;}
    public double getVal() {return val;}

} //End Class
