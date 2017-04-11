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
        diff= 1;
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

            values.add(recursion(copyGame, a.get(i), diff*2, 0));
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
        return max;
    }

    public double recursion(ReversiGame copy, Coordinate c, int depth, double val){
        if(depth == 0)
            return val;
        else{
            copy.move(copy.getCurrentPlayer(),c.getRowLocation(),c.getColLocation()); //makes the move on the board
            ArrayList<Coordinate> moves = copy.getCurrentPlayerMoves(); //finds the possible moves of the new move
            BestMove best = new BestMove(copy, moves); //finds the best new move
            Coordinate cord = new Coordinate((best.getBest().getRowLocation()), best.getBest().getColLocation()); //converts bestMove to coordinate
            val = best.getVal(); //gives the val of current move
            return recursion(copy, cord, depth-1, val);
        }
    }

}