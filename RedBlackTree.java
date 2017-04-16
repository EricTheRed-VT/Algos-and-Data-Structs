public class RedBlackTree<K extends Comparable<K>, V> {
    private Node root;

    private class Node {
        private K key;
        private V value;
        private int count;
        private Node left, right;

        private Node(K kee, V val) {
            key = kee;
            value = val;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node curr) {
        if (curr == null) { return 0; }
        else { return curr.count; }
    }

    //overloaded method that calls a recursive func
    public void add(K kee, V val) {
        root = add(root, kee, val);
    }

    //recursive func that returns the updated root
    private Node add(Node curr, K kee, V val) {
        // base case 1: insert a new node in the proper spot
        if (curr == null) { return new Node(kee, val); }

        int cmp = kee.compareTo(curr.key);
        if (cmp < 0) { curr.left = add(curr.left, kee, val); }
        else if (cmp > 0) { curr.right = add(curr.right, kee, val); }

        // base case 2: if key matches, update value
        else { curr.value = val; }

        curr.count = 1 + size(curr.left) + size(curr.right);
        return curr;
    }

    public V get(K kee) {
        Node curr = root;
        while (curr != null) {
            int cmp = kee.compareTo(curr.key);
            if (cmp < 0) { curr = curr.left; }
            else if (cmp > 0) { curr = curr.right; }
            else { return curr.value; }
        }
        // if not found, return null.
        return null;
    }

    public void delete(K kee) {

    }

    // overloaded method calls recursive func
    public K floor(K kee) {
        Node curr = floor(root, kee);
        if (curr == null) { return null; }
        return curr.key;
    }

    // recursive funci
    private Node floor(Node curr, K kee) {
        if (curr == null) { return null; }

        int cmp = kee.compareTo(curr.key);
        if (cmp < 0) { return floor(curr.left, kee); }
        else if (cmp == 0) { return curr; }

        Node rightfloor = floor(curr.right, kee);
        if (rightfloor != null) { return rightfloor; }
        else { return curr; }
    }

    //overloaded method
    public int rank(K kee) {
        return rank(root, kee);
    }

    //recursive func
    private int rank(Node curr, K kee) {
        if (curr == null) { return 0; }

        int cmp = kee.compareTo(curr.key);
        if (cmp < 0) { return rank(curr.left, kee); }
        else if (cmp > 0) {
            return 1 + size(curr.left) + rank(curr.right, kee);
        }
    }

    public Iterable<K> iterator() {

    }
}