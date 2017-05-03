package plu.blue.reversi.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.Coordinate;
import plu.blue.reversi.client.model.GameHistory;
import plu.blue.reversi.client.Move;
import plu.blue.reversi.client.model.Player;

/**
 * Class that tests GameHistory methods
 * @author Adam Grieger
 */
public class GameHistoryTest {

    private GameHistory gh;

    @Before
    public void init() {
        gh = new GameHistory();
    }

    @Test
    public void testAddMove() {
        Assert.assertEquals(4, gh.getMoveHistory().size());

        Player p1 = new Player("Black Player", -1);
        Coordinate c = new Coordinate(2, 3);

        Assert.assertTrue(gh.addMove(new Move(c, p1)));
        Assert.assertEquals(5, gh.getMoveHistory().size());
        Assert.assertEquals(2, gh.getMoveHistory().get(4).getCoordinate().getRowLocation());
        Assert.assertEquals(3, gh.getMoveHistory().get(4).getCoordinate().getColLocation());
    }

}
