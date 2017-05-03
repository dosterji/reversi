package plu.blue.reversi.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.helper.Coordinate;
import plu.blue.reversi.client.model.GameBoard;
import plu.blue.reversi.client.model.GameHistory;

import java.util.ArrayList;

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

    @Test
    public void getLegalMovesForPLayer1() {
        ArrayList<Coordinate> a = new ArrayList<Coordinate>();
        Assert.assertEquals( board1.toString(), new GameBoard(history.getMoveHistory()).toString());
        a = board1.getLegalMoves(1);
        Assert.assertEquals(4, a.size());
        Assert.assertEquals("D3", a.get(0).toString());
        Assert.assertEquals("C4", a.get(1).toString());
        Assert.assertEquals("F5", a.get(2).toString());
        Assert.assertEquals("E6", a.get(3).toString());
        }

    @Test
    public void getLegalMovesForPLayer2() {
        ArrayList<Coordinate> a = new ArrayList<Coordinate>();
        Assert.assertEquals( board1.toString(), new GameBoard(history.getMoveHistory()).toString());
        a = board1.getLegalMoves(-1);
        Assert.assertEquals(4, a.size());
        Assert.assertEquals("E3", a.get(0).toString());
        Assert.assertEquals("F4", a.get(1).toString());
        Assert.assertEquals("C5", a.get(2).toString());
        Assert.assertEquals("D6", a.get(3).toString());
    }
}