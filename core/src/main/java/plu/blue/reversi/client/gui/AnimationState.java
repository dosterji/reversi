package plu.blue.reversi.client.gui;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * This class is basically a stack, but it is slightly specialized
 * to always hold a special value of true.  This first value is the
 * only value allowed to be true in the stack.
 *
 * Created by John on 4/3/2017.
 */
public class AnimationState {
    private Stack threads;

    public AnimationState(){
        threads = new Stack();
        threads.push(true);
    }
    public void push() {
        threads.push(false);
    }
    public boolean pop() {
        if(size() == 0)
            throw new NoSuchElementException("ERROR: Nothing to pop");
        return (boolean)threads.pop();
    }
    public boolean peek() {
        return (boolean)threads.peek();
    }
    public boolean isEmpty() {
        if(size() == 0)
            return true;
        else
            return false;
    }
    public int size() {
        return threads.size() - 1;
    }
}
