package queue;

public class MyArrayElementsADTTest {
    public static void enqueue(ArrayQueueADT elements, String prefix) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(elements, prefix + i);
        }
    }

    public static void dequeue(ArrayQueueADT elements) {
        while (!ArrayQueueADT.isEmpty(elements)) {
            System.out.println(
                    ArrayQueueADT.size(elements) + " " +
                            ArrayQueueADT.element(elements) + " " +
                            ArrayQueueADT.dequeue(elements)
            );
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT elements1 = ArrayQueueADT.create();
        ArrayQueueADT elements2 = ArrayQueueADT.create();
        enqueue(elements1, "s1_");
        enqueue(elements2, "s2_");
        dequeue(elements1);
        dequeue(elements2);
    }
}
