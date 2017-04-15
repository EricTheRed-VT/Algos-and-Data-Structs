// Implemented with a singly-linked-list. I may add a
// separate implementation with ArrayLists or Vectors.
// Uses null value as an end-signifier.

import java.util.Iterator;

public class Queue<T> {
    private Node head = null;
    private Node tail = null;

    private class Node {
        T data;
        Node next;
    }

    public void enqueue(T item) {
        Node temp = tail;
        tail = new Node();
        tail.data = item;
        if (isEmpty()) { head = tail; }
        else { temp.next = temp; }
    }

    public T dequeue() {
        if (head == null) { return null; }
        T item = head.data;
        head = head.next;
        if (isEmpty()) { tail = null; }
        return item;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Iterator<T> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<T> {
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