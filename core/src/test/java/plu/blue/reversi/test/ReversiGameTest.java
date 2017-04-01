package plu.blue.reversi.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.Coordinate;
import plu.blue.reversi.client.ReversiGame;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test a few methods in the Reversi Game class
 * Created by John on 3/31/2017.
 */
public class ReversiGameTest {
    private ReversiGame game;

    @Before
    public void init() {
        game = new ReversiGame();
    }

    @Test
    public void adjustCoordForFlipping() throws Exception {
        Coordinate c = game.adjustCoordForFlipping(2,4,4,4);
        Assert.assertEquals("E4", c.toString());
    }

    @Test
    public void getCurrentPlayerMoves() throws Exception {
        ArrayList<Coordinate> a = new ArrayList<Coordinate>();
        a = game.getCurrentPlayerMoves();
        Assert.assertEquals(4, a.size());
        Assert.assertEquals("E3", a.get(0).toString());
        Assert.assertEquals("F4", a.get(1).toString());
        Assert.assertEquals("C5", a.get(2).toString());
        Assert.assertEquals("D6", a.get(3).toString());
    }

}