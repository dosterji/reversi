package plu.blue.reversi.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.Move;
import plu.blue.reversi.client.Player;
import plu.blue.reversi.client.Coordinate;

import static org.junit.Assert.*;

/**
 * Some Simple Tests for Move.
 * Created by John on 3/17/2017.
 */
public class MoveTest {
    private Move m1;
    private Move m2;
    Player p1 = new Player("WhitePlayer", -1);
    Player p2 = new Player("BlackPlayer", 1);

    @Before
    public void init() {
        m1 = new Move(new Coordinate(1,2),p1);
        m2 = new Move(new Coordinate(6,5), p2);
    }
    @Test
    public void getCoordinate() throws Exception {
        Assert.assertEquals( m1.getCoordinate().toString(), "C2");
        Assert.assertEquals( m2.getCoordinate().toString(), "F7");
    }

    @Test
    public void getPlayer() throws Exception {
        Assert.assertEquals("Player: WhitePlayer; Score: 2.", m1.getPlayer().toString());
        Assert.assertEquals("Player: BlackPlayer; Score: 2.", m2.getPlayer().toString());
    }
    @Test
    public void toStringTest() throws Exception {
        assertEquals("C2, Player: WhitePlayer; Score: 2.", m1.toString());
        assertEquals("F7, Player: BlackPlayer; Score: 2.", m2.toString());
    }

}