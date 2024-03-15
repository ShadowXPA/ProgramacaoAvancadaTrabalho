package pt.isec.a2019112789.connect4s.game.logic.data;

import java.util.Stack;

/**
 * Taken from:
 * https://stackoverflow.com/questions/7727919/creating-a-fixed-size-stack
 *
 * (April 2021)
 */
public class SizedStack<T> extends Stack<T> {

    private int maxSize;

    public SizedStack(int size) {
        super();
        this.maxSize = size;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        clearAndRemove();
    }

    private void clearAndRemove() {
        if (maxSize == 0) {
            this.clear();
        }
        while (this.size() >= maxSize) {
            this.remove(0);
        }
    }

    @Override
    public T push(T object) {
        clearAndRemove();
        if (maxSize == 0) {
            return null;
        }
        return super.push(object);
    }
}
