package plu.blue.reversi.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.Coordinate;
import plu.blue.reversi.client.GameHistory;
import plu.blue.reversi.client.Player;

/**
 * Class that tests GameHistory methods
 * @author Adam Grieger
 */
public class GameHistoryTest {

    private GameHistory gh;

    @Before
    public void init() {
        Player p1 = new Player("Black Player", 1);
        Player p2 = new Player("White Player", -1);
        gh = new GameHistory(p1, p2);
    }

    @Test
    public void testAddMove() {
        Assert.assertEquals(0, gh.getMoveHistory().size());
        Assert.assertTrue(gh.addMove(new Coordinate(2, 3)));
        Assert.assertEquals(1, gh.getMoveHistory().size());
        Assert.assertEquals(2, gh.getMoveHistory().get(0).getRowLocation());
        Assert.assertEquals(3, gh.getMoveHistory().get(0).getColLocation());
    }

}
