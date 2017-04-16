// simple Hash Table implemented with separate chaining
// I may implement a Linear Probing version later.
import Queue;

public class HashTable<K, V> {
    private int count;
    private int numLists;
    private Node[] lists;

    private static class Node {
        private K key;
        private V value;
        private Node next;

        public Node(K kee, V val, Node nex) {
            key = kee;
            value = val;
            next = nex;
        }
    }

    public HashTable() {
        return HashTable(997);
    }

    public HashTable(int siz) {
        numLists = siz;
        lists = new Node[siz];
    }

    private int hash(K kee) {
        return (key.hashCode() & 0x7fffffff) % numLists;
    }

    public int size() {
        return numLists;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(K kee) {
        return get(kee) != null;
    }

    public V get(K kee) {
        int hashed = hash(kee);
        for (Node curr = lists[hashed]; curr != null; curr = curr.next) {
            if (kee.equals(curr.key)) { return curr.value; }
        }
        return null;
    }

    public void insert(K kee, V val) {
        if (val == null) {
            delete(kee);
            return;
        }
        int hashed = hash(kee);
        for (Node curr = lists[hashed]; curr != null; curr = curr.next) {
            if (kee.equals(curr.key)) {
                curr.value = val;
                return;
            }
        }
        count++;
        lists[hashed] = new Node(kee, val, lists[hashed]);
    }

    public void delete(K kee) {
        int hashed = hash(kee);
        Node curr = lists[hashed];
        if (curr == null) { return; }

        // first node might be a match
        if (kee.equals(curr.key)) {
            lists[hashed] = curr.next;
            return;
        }

        //find and delete correct node
        for (; curr != null; curr = curr.next) {
            if (kee.equals(curr.next.key)) {
                curr.next = curr.next.next;
                return;
            }
        }
        return; //key not found
    }

    public Iterable<K> keys() {
        Queue<K> q = new Queue<K>();
        for (int i = 0; i < numLists; i++) {
            for (Node curr = lists[i]; curr != null; curr = curr.next) {
                q.enqueue(curr.key);
            }
        }
        return q;
    }
}