// http://algs4.cs.princeton.edu/13stacks/ResizingArrayQueue.java.html

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue
    private int first = 0;       // index of first element of queue
    private int last  = 0;       // index of next available slot

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
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
        for (int i = 0; i < N; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last  = N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        // double size of array if necessary and recopy to front of array
        if (N == q.length) {
            resize(2*q.length);   // double size of array if necessary
        }

        q[last++] = item;                        // add item

        if (last == q.length) {
            last = 0;          // wrap-around
        }
        N++;
    }
    
    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }

        Item item = q[first];
        q[first] = null;                            // to avoid loitering
        N--;
        first++;

        if (first == q.length) {
            first = 0;           // wrap-around
        }

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
        return q[first];
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
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

            Item item = q[(i + first) % q.length];
            i++;
            return item;
        }
    }

    
    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> q;
        int i;

        q = new RandomizedQueue<Integer>();
        q.enqueue(1);
        q.enqueue(2);
        assert q.size() == 2;
        i = q.dequeue();
        assert q.size() == 1;
        i = q.sample();
        assert q.size() == 1;
    }
}
