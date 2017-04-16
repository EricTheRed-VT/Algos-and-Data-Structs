// implemented as a weighted digraph, weights can
// be ignored, bi-directional edges can be added.
import Queue;

public class Graph {
    private final int numVertices;
    private EdgeNode[] edgeLists;

    private class EdgeNode {
        private int to;
        private EdgeNode next;

        public EdgeNode(int tu) {
            to = tu;
        }
    }
    
    public Graph(int verts) {
        numVertices = verts;
        edgeLists = new Node[numVertices];
        for (int i = 0; i < numVertices; i++) {
            edgeLists[i] = new Node();
        }
    }
    
    public void addEdge(int from, int to) {
        // see if edge is already present
        for (EdgeNode curr = edgeLists[from]; curr != null; curr = curr.next) {
            if (curr.to == to) { return; }
        }

        // add edge to beginning of list
        EdgeNode node = new EdgeNode(to);
        node.next = edgeLists[from];
        edgeLists[from] = node;
    }

    public void addBiEdge(int from, int to) {
        addEdge(from, to);
        addEdge(to, from);
    }

    public Iterable<Integer> adjacent(int vert) {
        Queue<Integer> q = new Queue<Integer>();
        for (EdgeNode curr = edgeLists[vert]; curr != null; curr = curr.next) {
            q.enqueue(curr.to);
        }
        return q;
    }
}