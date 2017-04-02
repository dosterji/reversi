package plu.blue.reversi.client;

import java.util.ArrayList;

/**
 * This class extends the Player class and indicates that the player is infact a CPU.
 * Created by John on 4/2/2017.
 */
public class CPU extends Player {
    private int diff; // The CPU difficulty
    private ReversiGame game; //a copy of the game.

    public CPU() {
        super("CPU", 1);
        diff= 1;
        game = null;
    }//End constructor

    public BestMove move(ReversiGame other) {
        game = new ReversiGame(other);
        ArrayList<Coordinate> a = game.getCurrentPlayerMoves();
        BestMove m = new BestMove(game, a);

        return m;
    }
}
