// http://algs4.cs.princeton.edu/13stacks/ResizingArrayQueue.java.html

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;            // queue elements
    private int N;               // number of elements on queue
    private int last;            // index of next available slot

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        last = 0;
        N = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }

    // resize the underlying array
    private void resize(int max) {
        assert max >= N;

        Item[] temp = (Item[]) new Object[max];
        int templast = 0;

        for (int i = 0; i < last; i++) {
            if (q[i] != null) {
                temp[templast] = q[i];
                templast++;
            }
        }

        q = temp;
        last = templast;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        // double size of array if necessary and recopy to front of array
        if (last == q.length - 1) {
            resize(2 * q.length);   // double size of array if necessary
        }

        q[last] = item;
        last++;

        N++;
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }

        Item item = null;
        while (item == null) {
            int r = StdRandom.uniform(last);
            item = q[r];
            q[r] = null;  // avoid loitering
        }
        assert item != null;
        N--;

        // shrink size of array if necessary
        if (N > 0 && N == q.length/4) {
            resize(q.length/2);
        }

        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }

        Item item = null;

        while (item == null) {
            int r = StdRandom.uniform(last);
            item = q[r];
        }
        assert item != null;

        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i;  // number of items returned
        private int o;  // index to the random order array
        private int[] order;

        public ArrayIterator() {
            i = 0;
            order = new int[last];

            for (int j = 0; j < last; j++) {
                order[j] = j;
            }

            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return i < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = null;
            while (item == null) {
                item = q[order[o]];
                o++;
            }
            i++;
            assert item != null;
            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> q;
        int i;

        q = new RandomizedQueue<Integer>();
        for (i = 0; i < 50; i++) {
            q.enqueue(i);
            assert q.size() == i + 1;
        }

        i = q.dequeue();
        StdOut.println("deque: " + i);
        assert q.size() == 49;

        for (i = 0; i < 5; i++) {
            StdOut.println("sample: " + q.sample());
        }

        for (i = 0; i < 40; i++) {
            q.dequeue();
        }

        q = new RandomizedQueue<Integer>();
        for (i = 0; i < 20; i++) {
            q.enqueue(i);
        }

        for (int j : q) {
            StdOut.println(j);
        }
    }
}
