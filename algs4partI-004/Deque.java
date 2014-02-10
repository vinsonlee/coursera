// http://algs4.cs.princeton.edu/13stacks/LinkedQueue.java.html

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node first;
    private Node last;

    // helper linked list class
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }
    
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return N;
    }
    
    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node oldfirst = first;

        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldfirst;

        if (isEmpty()) {
            last = first;
        } else {
            oldfirst.prev = first;
        }

        N++;
    }
    
    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node oldlast = last;

        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;

        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }

        N++;
    }
    
    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;
        N--;

        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }

        return item;
    }
    
    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;
        last = last.prev;
        N--;

        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }
        
        return item;
    }

    /**
     * Returns a string representation of this queue.
     * @return the sequence of items in FIFO order, separated by spaces
     */
    /*
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }
     */

    // check internal invariants
    private boolean check() {
        if (N == 0) {
            if (first != null) {
                return false;
            } else if (last  != null) {
                return false;
            }
        } else if (N == 1) {
            /* empty */
            return true;
        }
        return true;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    // unit testing
    public static void main(String[] args) {
        Deque<Integer> q;

        // Calls to addLast()
        q = new Deque<Integer>();
        for (int i = 0; i < 5; i++) {
            q.addLast(i);
        }
        //StdOut.println(q);

        // Calls to addFirst()
        q = new Deque<Integer>();
        for (int i = 0; i < 5; i++) {
            q.addFirst(i);
        }
        //StdOut.println(q);
    }

}
