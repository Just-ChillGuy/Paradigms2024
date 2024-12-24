package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    @Override
    protected void enqueueImpl(Object element) {
        if (head == null) {
            head = new Node(element);
            tail = head;
        } else {
            Node newTail = new Node(element);
            tail.next(newTail);
            tail = newTail;
        }
    }

    @Override
    protected Object dequeueImpl() {
        Object removedElement = head.value;
        head = head.next;

        return removedElement;
    }

    @Override
    protected Object elementImpl() {
        return head.value;
    }

    @Override
    protected void clearImpl() {
        head = null;
        tail = null;
    }

    private static class Node {
        private final Object value;
        private Node next;

        public Node(Object value) {
            assert value != null;
            this.value = value;
        }

        public void next(Node next) {
            this.next = next;
        }
    }
}
