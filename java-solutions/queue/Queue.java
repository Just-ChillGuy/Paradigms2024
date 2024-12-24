package queue;

// Model: a[head]..a[size] && a[0] = null
// Inv: size >= 0 && size <= a.length && forall i=1..size - 1: a[i] != null
// Let: immutable(k): forall i=1..k - 1
public interface Queue {
    //Pre: true
    //Post: elements'[i] = element && size' = size + 1
    void enqueue(Object element);

    //Pre: true
    //Post: forall i = [0, 4]: elements'[i] = null
    // && elements'.length = 5 && head' = 0 && size' = 0
    void clear();

    //Pre: true
    //Post: size > 1 && a[i] != a[i + 1] && (x < y) && x forall 1..y - 1: a[x] != a[y] && a[y] -> a'[y'] && a[x] -> a'[y' - 1]
    void dedup();

    //Pre: n > 0
    //Post: elements'[head] = null
    // && head' = head + 1 && size' = size - 1
    Object dequeue();

    //Pre: n > 0
    //Post: return elements[head] && immutable(size)
    Object element();

    //Pre: true
    //Post: return size && immutable(size)
    int size();

    //Pre: true
    //Post: return size == 0 && immutable(size)
    boolean isEmpty();
}
