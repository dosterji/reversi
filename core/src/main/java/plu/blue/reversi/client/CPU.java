package plu.blue.reversi.client;

import java.util.ArrayList;

/**
 * This class extends the Player class and indicates that the player is infact a CPU.
 * Created by John on 4/2/2017.
 */
public class CPU extends Player {
    private int diff; // The CPU difficulty TODO: pass this value to the constructor to allow deeper difficulties
    private ReversiGame game; //a copy of the game as it is currently being run..

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

        ArrayList<Coordinate> a = game.getCurrentPlayerMoves();
        BestMove m = new BestMove(game, a);

        return m.getBest();
    }
}
