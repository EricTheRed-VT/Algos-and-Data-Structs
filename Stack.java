// Implemented with a singly-linked-list. I may add a
// separate implementation with ArrayLists or Vectors.
// Uses null value as an end-signifier.

import java.util.Iterator;

public class Stack<T> {
    private Node head = null;

    private class Node {
        T data;
        Node next;
    }

    public void push(T item) {
        Node temp = head;
        head = new Node();
        head.next = temp;
        head.data = item;
    }

    public T pop() {
        if (head == null) { return null; }
        T item = head.data;
        head = head.next;
        return item;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Iterator<T> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {/* unsupported, does nothing */}

        public T next() {
            if (current == null) { return null; }
            T item = current.data;
            current = current.next;
            return item;
        }
    }
}