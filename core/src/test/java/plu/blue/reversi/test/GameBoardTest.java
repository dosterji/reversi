package plu.blue.reversi.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.Coordinate;
import plu.blue.reversi.client.GameBoard;
import plu.blue.reversi.client.GameHistory;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by John on 3/20/2017.
 * Tests the Game BOard Methods
 */
public class GameBoardTest {
    private GameHistory history;
    private GameBoard board1;

    @Before
    public void init() {
        history = new GameHistory();
        board1 = new GameBoard(history.getMoveHistory());
    }
    @Test
    public void isLegalMove() throws Exception {
        Assert.assertEquals(null, board1.isLegalMove(-1,0,0));
        Assert.assertEquals(null, board1.isLegalMove(1,0,0));
        Assert.assertEquals(null, board1.isLegalMove(-1,7,7));
        Assert.assertEquals(null, board1.isLegalMove(1,4,2));
        Assert.assertEquals(1, board1.isLegalMove(-1,4,2).size());
        Assert.assertEquals(null, board1.isLegalMove(-1,6,2));
    }

    @Test
    public void update() throws Exception {
        Assert.assertEquals( board1.toString(), new GameBoard(history.getMoveHistory()).toString());
        board1.update(-1,4,2);
        String s = "  -  -  -  -  -  -  -  -\n" +
                "  -  -  -  -  -  -  -  -\n" +
                "  -  -  -  -  -  -  -  -\n" +
                "  -  -  -  B  W  -  -  -\n" +
                "  -  -  B  W  B  -  -  -\n" +
                "  -  -  -  -  -  -  -  -\n" +
                "  -  -  -  -  -  -  -  -\n" +
                "  -  -  -  -  -  -  -  -\n";
        Assert.assertEquals(s, board1.toString());
    }
}