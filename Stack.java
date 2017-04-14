public class Stack<E> {
    private Node head = null;

    private class Node {
        E data;
        Node next;
    }

    public void push(E item) {
        Node temp = head;
        head = new Node();
        head.next = temp;
        head.data = item;
    }

    public E pop() {
        if (!head) { return null; }
        E item = head.data;
        head = head.next;
        return item;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Iterator<E> iterator() {
        return new StackIterator();
    }
}