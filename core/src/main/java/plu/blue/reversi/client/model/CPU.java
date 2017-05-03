package plu.blue.reversi.client.model;
import plu.blue.reversi.client.helper.BestMove;
import plu.blue.reversi.client.helper.Coordinate;

import java.util.ArrayList;
/**
 * This class extends the Player class and indicates that the player is infact a CPU.
 * Created by John on 4/2/2017.
 */
public class CPU extends Player {
    private int diff; // The CPU difficulty TODO: pass this value to the constructor to allow deeper difficulties
    private ReversiGame game, copyGame; //a copy of the game as it is currently being run..
    public CPU(int difficulty) {
        super("CPU", 1);
        diff= difficulty;
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
        ArrayList<Coordinate> a = game.getCurrentPlayerMoves();
        ArrayList<Double> values = new ArrayList<Double>();


        //Loop for getting the best value for each node
        for(int i = 0; i <a.size(); i++) {
            //
            copyGame = new ReversiGame(other);
            BestMove move = new BestMove(a.get(i), copyGame); //creates a node from the Coordinate, places the pieces down on the board
            copyGame.move(copyGame.getCurrentPlayer(), move.getBest().getRowLocation(), move.getBest().getColLocation());
            //System.out.println("VALUE OF NODE : " + i + ": " + move.getValue());
            values.add(recursion1(copyGame, move, diff, 1, -1));
            System.out.print("Coordinate: " + a.get(i).toString());
            System.out.println(" value: " + values.get(i));
        }

        Coordinate max = a.get(0);
        double maxV = values.get(0);
        System.out.printf("ARRAY VALS(%d): ", values.size());
        System.out.printf("%f, ", values.get(0));

        //loop for determining to best value for the CPU move
        for(int i = 1; i < values.size(); i++){
            if(values.get(i) > maxV){
                max = a.get(i);
                maxV = values.get(i);
            }
            System.out.printf("%f, ", values.get(i));
        }
        System.out.printf("\nPOSS MOVES(%d): ", a.size());
        System.out.printf("%s", a.get(0).toString());

        //Just a print loop
        for(int i = 1; i < a.size(); i++) {
           System.out.printf(" %s", a.get(i).toString());
        }

        System.out.println("\nThe best move to make is " + max.getColLocation() +" " + max.getRowLocation() + ": " + maxV);
        return max;
    }

    /**
     * The recursive method
     */
    public double recursion1(ReversiGame copy, BestMove node, int depth, double beta, double alpha){
        if(depth == 8 || copy.getCurrentPlayerMoves().size() == 0 || node.getBest().isCorner()){
            System.out.println("Returning value of: " + node.getValue() + " AT DEPTH 8");
            return node.getValue();
        }

        ArrayList<Coordinate> list = copy.getCurrentPlayerMoves();
        if(copy.getCurrentPlayerColor() == -1){ //black aka HUMAN
            System.out.println("HUMAN PLAYER");
            double best = 1;
            for(int i = 0; i < list.size(); i++){
                copy.move(copy.getCurrentPlayer(), list.get(i).getRowLocation(), list.get(i).getColLocation());
                System.out.println("Passing ALPHA " + alpha  + "BETA : " + beta);
                best = min(best, recursion1(copy, new BestMove(list.get(i), copy),depth+1,beta, alpha));
                beta = min(beta, best);
                if(beta <= alpha){
                    System.out.println(beta + " < " + alpha);
                    //System.out.println("PRUNE");
                    break;
                }
            }
            System.out.println("RETURNING BEST BLACK: " + best);
            return best;
        }
        else{ //white aka CPU
            System.out.println("CPU PLAYER");
            double best = -1;
            for(int i = 0; i < list.size(); i++){
                copy.move(copy.getCurrentPlayer(), list.get(i).getRowLocation(), list.get(i).getColLocation());
                System.out.println("Passing ALPHA " + alpha  + "BETA : " + beta);
                best = max(best, recursion1(copy, new BestMove(list.get(i), copy),depth+1,beta, alpha));
                alpha = max(alpha, best);
                if(beta <= alpha){
                    System.out.println(beta + " < " + alpha);
                    System.out.println("PRUNE");
                    break;
                }
            }
            System.out.println("RETURNING BEST WHITE : " + best);
            return best;
        }
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