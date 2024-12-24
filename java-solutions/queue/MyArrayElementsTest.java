package queue;

public class MyArrayElementsTest {
    public static void enqueue(ArrayQueue elements, String prefix) {
        for (int i = 0; i < 10; i++) {
            elements.enqueue(prefix + i);
        }
    }

    public static void dequeue(ArrayQueue elements) {
        while (!elements.isEmpty()) {
            System.out.println(elements.size() + " " + elements.dequeue());
        }
    }

    public static void main(String[] args) {
        ArrayQueue elements1 = new ArrayQueue();
        ArrayQueue elements2 = new ArrayQueue();
        enqueue(elements1, "s1_");
        enqueue(elements2, "s2_");
        dequeue(elements1);
        dequeue(elements2);
    }
}
