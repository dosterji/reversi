package plu.blue.reversi.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.Coordinate;

/**
 * A test for a few methods in the coordinate class
 * Created by John on 4/23/2017.
 */
public class CoordinateTest {
   Coordinate same1, same2, diff;

    @Before
    public void init() {
        same1 = new Coordinate(1,20);
        same2 = new Coordinate(1,20);
        diff = new Coordinate(1,2);
    }
    @Test
    public void equals() throws Exception {
        Assert.assertEquals(true, same1.equals(same2));
        Assert.assertEquals(true, same2.equals(same1));
        Assert.assertEquals(false, same1.equals(diff));
        Assert.assertEquals(false, same2.equals(diff));
        Assert.assertEquals(false, diff.equals(same1));
    }

    @Test
    public void isCorner() throws Exception {
        Assert.assertEquals(false, same1.isCorner());
        Assert.assertEquals(true, new Coordinate(7,7).isCorner());
        Assert.assertEquals(true, new Coordinate(0,7).isCorner());
        Assert.assertEquals(false, diff.isCorner());
    }

}