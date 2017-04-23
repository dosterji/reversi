package plu.blue.reversi.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests the BestMoveClass
 * Created by John on 4/2/2017.
 */
public class BestMoveTest {
    ReversiGame game;
    ReversiGame basic;


    @Before
    public void init() {
        basic = new ReversiGame();
        game = new ReversiGame();
    }
    @Test
    public void TestNode() throws Exception {
        double t = 0.0/10.0;
        System.out.println(t);
        BestMove m = new BestMove(game, game.getCurrentPlayerMoves());
        Assert.assertEquals("E3", m.getBest().toString());
        Assert.assertEquals(basic.toString(), game.toString());
        Assert.assertEquals(-0.6, m.getValue(), 0);

        game.move(game.getCurrentPlayer(), m.getBest().getRowLocation(), m.getBest().getColLocation());

        m = new BestMove(game, game.getCurrentPlayerMoves());
        Assert.assertEquals("D3", m.getBest().toString());
        //Assert.assertEquals(0, m.getValue(), 0);

        game.move(game.getCurrentPlayer(), m.getBest().getRowLocation(), m.getBest().getColLocation());

        m = new BestMove(game, game.getCurrentPlayerMoves());
        Assert.assertEquals("C3", m.getBest().toString());
        Assert.assertEquals(-0.7142, m.getValue(), 0.0001);

        game.move(game.getCurrentPlayer(), m.getBest().getRowLocation(), m.getBest().getColLocation());

        m = new BestMove(game, game.getCurrentPlayerMoves());
        Assert.assertEquals("D2", m.getBest().toString());
        //Assert.assertEquals(0, m.getValue(), 0);

        game.move(game.getCurrentPlayer(), m.getBest().getRowLocation(), m.getBest().getColLocation());

        m = new BestMove(game, game.getCurrentPlayerMoves());
        Assert.assertEquals("C5", m.getBest().toString());
        Assert.assertEquals(-0.5555, m.getValue(), 0.0001);
    }
}