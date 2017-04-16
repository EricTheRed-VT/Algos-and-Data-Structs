// an implementation of a left-leaning Red/Black Binary Search Tree

public class RedBlackTree<K extends Comparable<K>, V> {
    private Node root;

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        private K key;
        private V value;
        private int count;
        private boolean color;
        private Node left, right;

        private Node(K kee, V val, boolean col, int count) {
            key = kee;
            value = val;
            color = col;
        }
    }

    private boolean isRed(Node curr) {
        if (curr == null) { return false; }
        return curr.color;
    }

    // flip a right-leaning red link to the left
    private Node rotateLeft(Node curr) {
        Node next = head.right;
        assert isRed(next);

        head.right = next.left;
        next.left = head;
        next.color = head.color;
        head.color = RED;
        return next;
    }

    // flip a left-leaning red link to the right (for temporary use)
    private Node rotateRight(Node curr) {
        Node next = head.left;
        assert isRed(next);

        head.left = next.right;
        next.right = head;
        next.color = head.color;
        head.color = RED;
        return next;
    }

    // if a black node has two red children, flip all three colors
    private void flipColors(Node head) {
        assert !isRed(head);
        assert isRed(head.left);
        assert isRed(head.right);

        head.color = RED;
        head.left.color = BLACK;
        head.right.color = BLACK;
    }

    public int size() {
        return size(root);
    }
    private int size(Node curr) {
        if (curr == null) { return 0; }
        else { return curr.count; }
    }

    public void add(K kee, V val) {
        if (kee == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (val == null) {
            delete(kee);
            return;
        }
        root = add(root, kee, val);
        root.color = BLACK;
    }
    private Node add(Node curr, K kee, V val) {
        // base case 1: insert a new node in the proper spot
        if (curr == null) { return new Node(kee, val, RED, 1); }

        int cmp = kee.compareTo(curr.key);
        if (cmp < 0) { curr.left = add(curr.left, kee, val); }
        else if (cmp > 0) { curr.right = add(curr.right, kee, val); }
        else { curr.value = val; } // base case 2: if key matches, update value

        if (isRed(curr.right) && !isRed(curr.left)) {
            // fix a right-leaning red link
            curr = rotateLeft(curr);
        }
        if (isRed(curr.left) && isRed(curr.left.left)) {
            // balance a 4-node
            curr = rotateRight(curr);
        }
        if (isRed(curr.left) && isRed(curr.right)) {
            // split a 4-node
            curr = flipColors(curr);
        }

        curr.count = 1 + size(curr.left) + size(curr.right);
        return curr;
    }

    public V get(K kee) {
        if (key == null) {
            throw new IllegalArgumentException("Argument to get() is null");
        }
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

    public boolean contains(K kee) {
        return get(kee) != null;
    }

    public K min() {
        return min(root);
    }
    private K min(Node curr) {
        if (curr == null) { return null; }

        while (curr.left != null) {
            curr = curr.left
        }
        return curr.key;
    }

    public K max() {
        return max(root);
    }
    private K max(Node curr) {
        if (curr == null) { return null; }

        while (curr.right != null) {
            curr = curr.right
        }
        return curr.key;
    }

    // overloaded method calls recursive func
    public K floor(K kee) {
        Node curr = floor(root, kee);
        if (curr == null) { return null; }
        return curr.key;
    }
    // recursive func
    private Node floor(Node curr, K kee) {
        if (curr == null) { return null; }

        int cmp = kee.compareTo(curr.key);
        if (cmp < 0) { return floor(curr.left, kee); }
        else if (cmp == 0) { return curr; }

        Node rightfloor = floor(curr.right, kee);
        if (rightfloor != null) { return rightfloor; }
        else { return curr; }
    }

    // overloaded method calls recursive func
    public K ceiling(K kee) {
        Node curr = ceiling(root, kee);
        if (curr == null) { return null; }
        return curr.key;
    }
    // recursive func
    private Node ceiling(Node curr, K kee) {
        if (curr == null) { return null; }

        int cmp = kee.compareTo(curr.key);
        if (cmp > 0) { return ceiling(curr.right, kee); }
        else if (cmp == 0) { return curr; }

        Node leftceiling = ceiling(curr.left, kee);
        if (leftceiling != null) { return leftceiling; }
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
        } else { return size(curr.left); }
    }

    public void deleteMin() {
        root = deleteMin(root);
    }
    private Node deleteMin(Node curr) {
        if (curr.left == null) { return curr.right; }
        curr.left = deleteMin(curr.left);
        curr.count = 1 + size(curr.left) + size(curr.right);
        return curr;
    }

    public void deleteMax() {
        root = deleteMax(root);
    }
    private Node deleteMax(Node curr) {
        if (curr.right == null) { return curr.left; }
        curr.right = deleteMax(curr.right);
        curr.count = 1 + size(curr.left) + size(curr.right);
        return curr;
    }

    public void delete(K kee) {
        root = delete(root, kee);
    }
    private Node delete(Node curr, K kee) {
        if (curr == null) { return null; }

        int cmp = kee.compareTo(curr.key);
        if (cmp < 0) { curr.left = delete(curr.left, kee); }
        else if (cmp > 0) { curr.right = delete(curr.right, kee); }
        //otherwise, found the node to delete
        else {
            if (curr.right == null) { return curr.left; }

            Node temp = curr;
            curr = min(temp.right);
            curr.right = deleteMin(temp.right);
            curr.left = temp.left;
        }
        curr.count = 1 + size(curr.left) + size(curr.right);
        return curr;
    }

    // returns a queue of the keys in order
    public Iterable<K> keys() {
        Queue<K> q = new Queue<K>();
        inorder(root, q);
        return q;
    }
    // traverse in order, adding keys to a queue
    private void inorder(Node curr, Queue<K> q) {
        if (curr == null) { return; }
        inorder(curr.left, q);
        q.enqueue(curr.key);
        inorder(curr.right, q);
    }
}