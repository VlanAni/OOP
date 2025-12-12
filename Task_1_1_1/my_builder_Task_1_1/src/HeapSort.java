/**
 * Class that sort array using Heap-object.
 */
public class HeapSort {

    private HeapSort() {
    }

    /**
     * Static method that creates Heap-object and call <code>sort</code>.
     * It uses the Heap-sort algorithm.
     * Time complexity - O(N*log(N)).
     *
     * @param array - user's array.
     */
    static void sortArr(int[] array) {
        if (array == null) {
            return;
        }
        var heap = new Heap();
        heap.sort(array);
    }
}