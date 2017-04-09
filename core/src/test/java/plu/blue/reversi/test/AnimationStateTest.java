package plu.blue.reversi.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.gui.AnimationState;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Tests the methods in AnimationState
 * Created by John on 4/3/2017.
 */
public class AnimationStateTest {
    private AnimationState s;
    @Before
    public void init() {
        s = new AnimationState();
    }
    @Test
    public void push() throws Exception {
        Assert.assertEquals(true, s.peek());
        s.push();
        s.push();
        Assert.assertEquals(false, s.peek());
        Assert.assertEquals(false, s.peek());
        Assert.assertEquals(2, s.size());
        Assert.assertEquals(false, s.pop());
        Assert.assertEquals(1, s.size());
    }

    @Test
    public void pop() throws Exception {
        Assert.assertEquals(0, s.size());
        s.push();
        s.push();
        s.push();
        s.push();
        Assert.assertEquals(4, s.size());
        Assert.assertEquals(false, s.pop());
        Assert.assertEquals(3, s.size());
        s.pop();
        s.pop();
        Assert.assertEquals(1, s.size());
    }

    @Test (expected = NoSuchElementException.class)
    public void pop2() throws Exception {
        s.pop();
    }
    @Test
    public void peek() throws Exception {
        Assert.assertEquals(true, s.peek());
        Assert.assertEquals(true, s.isEmpty());
        s.push();
        s.push();
        Assert.assertEquals(false, s.peek());
        Assert.assertEquals(2, s.size());
    }

}