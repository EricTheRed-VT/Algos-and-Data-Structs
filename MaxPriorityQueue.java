// Uses a variable-sized array as a heap, where the item at
// index i is the parent of the items at 2i and 2i+1.

public class MaxPriorityQueue<T extends Comparable<T>> {
    private T[] arr;
    private int size;

    public MaxPriorityQueue() {
        MaxPriorityQueue(10);
    }

    public MaxPriorityQueue(int startCapacity) {
        arr = (T[]) new Comparable[startCapacity+1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(t item) {
        if (arr.length-1 < ++size) {
            //TODO: expand array
        }
        arr[size] = item;
        swim(size);
    }

    public T takeMax() {
        T max = arr[1];
        swap(1, size--);
        sink(1);
        arr[size+1] = null;
        if (size < arr.length/4) {
            //TODO: shrink array
        }
        return max;
    }

    private boolean less(int i, int j) {
        return arr[i].compareTo(arr[j]) < 0;
    }

    private void swap(int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void swim(int i) {
        while (i > 1 && less(i/2, i)) {
            swap(i, i/2);
            i = i/2;
        }
    }

    private void sink(int i) {
        int j;

        while (i*2 <= size) {
            j = i*2;
            if (j < size && less(j, j+1)) { j++; }
            if (!less(i, j)) break;
            swap(i, j);
            i = j;
        }
    }
}
