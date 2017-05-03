package plu.blue.reversi.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.helper.BestMove;
import plu.blue.reversi.client.helper.Coordinate;
import plu.blue.reversi.client.model.ReversiGame;

import java.util.ArrayList;

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

    @Test
    public void TestConstr2() {
        ArrayList<Coordinate> a = game.getCurrentPlayerMoves();
        BestMove b = new BestMove(a.get(0), game);
        Assert.assertEquals(game.toString(), basic.toString());
        Assert.assertEquals(game.getCurrentPlayer().toString(), basic.getCurrentPlayer().toString());
        Assert.assertEquals(-3.0/5, b.getValue(), 1);
    }

    @Test
    public void TestNode2() {
        game.move(game.getCurrentPlayer(), 2, 4);
        //System.out.println(game.toString());
        game.move(game.getCurrentPlayer(), 2, 3);
        //System.out.println(game.toString());
        game.move(game.getCurrentPlayer(), 2, 2);
        //System.out.println(game.toString());

        ArrayList<Coordinate> a = game.getCurrentPlayerMoves();
        ArrayList<BestMove> b = new ArrayList<BestMove>();
        for(int i = 0; i < a.size(); i++) {
            b.add( new BestMove(a.get(i), game));
        }
        Assert.assertEquals(0, b.get(0).getValue(), 1);
        Assert.assertEquals(-2.0/8, b.get(1).getValue(), 1);
        Assert.assertEquals(-2.0/8, b.get(2).getValue(), 1);
        System.out.println(b.size());
        //System.out.println(a.size());
    }
}