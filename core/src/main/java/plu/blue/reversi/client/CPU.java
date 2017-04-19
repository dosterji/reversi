package plu.blue.reversi.client;
import java.util.ArrayList;
/**
 * This class extends the Player class and indicates that the player is infact a CPU.
 * Created by John on 4/2/2017.
 */
public class CPU extends Player {
    private int diff; // The CPU difficulty TODO: pass this value to the constructor to allow deeper difficulties
    private ReversiGame game, copyGame; //a copy of the game as it is currently being run..
    public CPU() {
        super("CPU", 1);
        diff= 4;
        game = null;
    }//End constructor

    /**
     * Finds the best move for the CPU at this moment in time.
     * DOesn't think ahead.  Returns the coordinate where the CPU
     * wants to place a piece.
     *
     * @param other A copy of the reversi game.
     * @return The place the CPU wants to play
     */
    public Coordinate move(ReversiGame other) {
        game = new ReversiGame(other);
        copyGame = game;
        ArrayList<Coordinate> a = game.getCurrentPlayerMoves();
        ArrayList<Double> values = new ArrayList<Double>();



        for(int i = 0; i <a.size(); i++) {
            //
            BestMove move = new BestMove(a.get(i), game); //creates a node from the Coordinate, places the pieces down on the board
            //System.out.println("VALUE OF NODE : " + i + ": " + move.getValue());
            values.add(recursion1(copyGame, move, 0, 1, -1));
            System.out.print("Coordinate: " + a.get(i).toString());
            System.out.println(" value: " + values.get(i));
        }

        Coordinate max = a.get(0);
        double maxV = values.get(0);
        for(int i = 1; i < values.size(); i++){
            if(values.get(i) > maxV){
                max = a.get(i);
                maxV = values.get(i);
            }
        }

        System.out.println("The best move to make is " + max.getColLocation() +" " + max.getRowLocation() + ": " + maxV);
        return max;
    }
    /*
    public double recursion(ReversiGame copy, Coordinate c, int depth,double beta, double alpha, double val){
        if(depth == 0) {

            return val;
        }
        else{
            copy.move(copy.getCurrentPlayer(),c.getRowLocation(),c.getColLocation()); //makes the move on the board
            ArrayList<Coordinate> moves = copy.getCurrentPlayerMoves(); //finds the possible moves of the new move of the opponent (for black aka human)
            if(copy.getCurrentPlayerColor() == 1) { //if the current player is white aka the CPU
                double best = -1;
                for (int i = 0; i < moves.size(); i++) { //for loop to look at all possible moves
                    best = max(best, recursion(copy, moves(i), depth-1, alpha, ))
                    if (beta < alpha);
                    //prune


                }
            }
            BestMove best = new BestMove(copy, moves); //finds the best new move
            Coordinate cord = new Coordinate((best.getBest().getRowLocation()), best.getBest().getColLocation()); //converts bestMove to coordinate
            val = best.getVal(); //gives the val of current move
            return recursion(copy, cord, depth-1, val);
        }
    }*/

    public double recursion1(ReversiGame copy, BestMove node, int depth,double beta, double alpha){
        if(depth == 8){
            System.out.println("Returning value of: " + node.getValue() + " AT DEPTH 4");
            return node.getValue();
        }
        ArrayList<Coordinate> list = copy.getCurrentPlayerMoves();

        if(copy.getCurrentPlayerColor() == 1){ //black aka HUMAN
            double best = 1;
            for(int i = 0; i < list.size(); i++){
                copy.move(copy.getCurrentPlayer(), list.get(i).getRowLocation(), list.get(i).getColLocation());
                System.out.println("Passing ALPHA " + alpha  + "BETA : " + beta);
                best = min(best, recursion1(copy, new BestMove(list.get(i), copy),depth+1,beta, alpha));
                beta = min(beta, best);
                if(beta <= alpha){
                    System.out.println(beta + " < " + alpha);
                    break;
                }
            }
            System.out.println("RETURNING BEST : " + best);
            return best;
        }
        if(copy.getCurrentPlayerColor() == -1){ //white aka CPU
            double best = -1;
            for(int i = 0; i < list.size(); i++){
                copy.move(copy.getCurrentPlayer(), list.get(i).getRowLocation(), list.get(i).getColLocation());
                System.out.println("Passing ALPHA " + alpha  + "BETA : " + beta);
                best = max(best, recursion1(copy, new BestMove(list.get(i), copy),depth+1,beta, alpha));
                alpha = max(beta, best);
                if(beta <= alpha){
                    System.out.println(beta + " < " + alpha);
                    System.out.println("PRUNE");
                    break;
                }
            }
            System.out.println("RETURNING BEST : " + best);
            return best;
        }
        return 0;
    }

    private double max(double a, double b){
        if(a > b)
            return a;
        else
            return b;
    }

    private double min(double a, double b){
        if(a < b)
            return a;
        else
            return b;
    }


}