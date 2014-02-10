// http://algs4.cs.princeton.edu/13stacks/LinkedQueue.java.html

import java.util.Iterator;

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
        return first == last;
    }
    
    // return the number of items on the deque
    public int size() {
        return N;
    }
    
    // insert the item at the front
    public void addFirst(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldfirst;
        oldfirst.prev = first;
        N++;
    }
    
    // insert the item at the end
    public void addLast(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        oldlast.next = last;
        N++;
    }
    
    // delete and return the item at the front
    public Item removeFirst() {
        Item item = first.item;
        first = first.next;
        first.prev = null;
        N--;
        return item;
    }
    
    // delete and return the item at the end
    public Item removeLast() {
        Item item = last.item;
        
        last = last.prev;
        last.next = null;
        
        N--;
        return item;
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
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    // unit testing
    public static void main(String[] args) {
        
    }
    
}
