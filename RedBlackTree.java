// an implementation of a left-leaning Red/Black Binary Search Tree
import Queue;
import java.util.NoSuchElementException;

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
        Node next = curr.right;
        assert isRed(next);

        curr.right = next.left;
        next.left = curr;
        next.color = curr.color;
        curr.color = RED;
        next.count = curr.count;
        curr.count = 1 + size(curr.left) + size(curr.right);
        return next;
    }

    // flip a left-leaning red link to the right (for temporary use)
    private Node rotateRight(Node curr) {
        Node next = curr.left;
        assert isRed(next);

        curr.left = next.right;
        next.right = curr;
        next.color = curr.color;
        curr.color = RED;
        next.count = curr.count;
        curr.count = 1 + size(curr.left) + size(curr.right);
        return next;
    }

    // flips the color of a node and both its children
    private void flipColors(Node head) {
        head.color = !head.color;
        head.left.color = !head.left.color;
        head.right.color = !head.right.color;
    }

    private Node moveRedLeft(Node curr) {
        flipColors(curr);
        if (isRed(curr.right.left)) {
            curr.right = rotateRight(curr.right);
            curr = rotateLeft(curr);
            flipColors(curr);
        }
        return curr;
    }

    private Node moveRedRight(Node curr) {
        flipColors(curr);
        if (isRed(curr.left.left)) {
            curr = rotateRight(curr);
            flipColors(curr);
        }
        return curr;
    }

    private Node balance(Node curr) {
        if (isRed(curr.right)) { curr = rotateLeft(curr); }
        if (isRed(curr.left) && isRed(curr.left.left)) {
            curr = rotateRight(curr);
        }
        if (isRed(curr.right) && isRed(curr.right)) { flipColors(curr); }

        curr.count = 1 + size(curr.left) + size(curr.right);
        return curr;
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
        if (root == null) { throw new NoSuchElementException("called min() with empty tree"); }
        return min(root).key;
    }
    private Node min(Node curr) {
        if (curr.left == null) { return curr; }
        else { return min(curr.left); }
    }

    public K max() {
        if (root == null) { throw new NoSuchElementException("called max() with empty tree"); }
        return max(root).key;
    }
    private Node max(Node curr) {
        if (curr.right == null) { return curr; }
        else { return max(curr.right); }
    }

    public K floor(K kee) {
        if (kee == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        if (root == null) {
            throw new NoSuchElementException("called floor() with empty tree");
        }
        Node curr = floor(root, kee);
        if (curr == null) { return null; }
        return curr.key;
    }
    private Node floor(Node curr, K kee) {
        if (curr == null) { return null; }

        int cmp = kee.compareTo(curr.key);
        if (cmp < 0) { return floor(curr.left, kee); }
        else if (cmp == 0) { return curr; }

        Node rightfloor = floor(curr.right, kee);
        if (rightfloor != null) { return rightfloor; }
        else { return curr; }
    }

    public K ceiling(K kee) {
        if (kee == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        if (root == null) {
            throw new NoSuchElementException("called ceiling() with empty tree");
        }
        Node curr = ceiling(root, kee);
        if (curr == null) { return null; }
        return curr.key;
    }
    private Node ceiling(Node curr, K kee) {
        if (curr == null) { return null; }

        int cmp = kee.compareTo(curr.key);
        if (cmp > 0) { return ceiling(curr.right, kee); }
        else if (cmp == 0) { return curr; }

        Node leftceiling = ceiling(curr.left, kee);
        if (leftceiling != null) { return leftceiling; }
        else { return curr; }
    }

    public int rank(K kee) {
        if (kee == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(root, kee);
    }
    private int rank(Node curr, K kee) {
        if (curr == null) { return 0; }

        int cmp = kee.compareTo(curr.key);
        if (cmp < 0) { return rank(curr.left, kee); }
        else if (cmp > 0) {
            return 1 + size(curr.left) + rank(curr.right, kee);
        } else { return size(curr.left); }
    }

    public void deleteMin() {
        if (root == null) { throw new NoSuchElementException("Tree underflow"); }

        if (!isRed(root.left) && !isRed(root.right)) { root.color = RED; }
        root = deleteMin(root);
        if (root != null) { root.color = BLACK; }
    }
    private Node deleteMin(Node curr) {
        if (curr.left == null) { return null; }

        if (!isRed(curr.left) && !isRed(curr.left.left)) {
            curr = moveRedLeft(curr);
        }

        curr.left = deleteMin(curr.left);
        return balance(curr);
    }

    public void deleteMax() {
        if (root == null) { throw new NoSuchElementException("Tree underflow"); }

        if (!isRed(root.left) && !isRed(root.right)) { root.color = RED; }
        root = deleteMax(root);
        if (root != null) { root.color = BLACK; }
    }
    private Node deleteMax(Node curr) {
        if (isRed(curr.left)) { curr = rotateRight(curr); }
        if (curr.left == null) { return null; }

        if (!isRed(curr.right) && !isRed(curr.right.left)) {
            curr = moveRedRight(curr);
        }
        curr.right = deleteMax(curr.right);
        return balance(curr);
    }

    public void delete(K kee) {
        if (kee == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        if (!contains(kee)) { return; }

        if (!isRed(root.left) && !isRed(root.right)) { root.color = RED; }

        root = delete(root, kee);
        if (root != null) { root.color = BLACK; }
    }
    private Node delete(Node curr, K kee) {
        if (kee.compareTo(curr.key) < 0) {
            if (!isRed(curr.left) && !isRed(curr.left.left)) {
                curr = moveRedLeft(curr);
            }
            curr.left = delete(curr.left, kee);
        } else {
            if (isRed(curr.left)) { curr = rotateRight(curr); }
            if ((kee.compareTo(curr.key) == 0) && (curr.right == null)) {
                return null;
            }
            if (!isRed(curr.right) && !isRed(curr.right.left)) {
                curr = moveRedRight(curr);
            }
            if (kee.compareTo(curr.key) == 0) {
                Node next = min(curr.right);
                curr.key = next.key;
                curr.value = next.value;
                curr.right = deleteMin(curr.right);
            } else { curr.right = delete(curr.right, key); }
        }
        return balance(curr);
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