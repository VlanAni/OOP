class Main {

    public static void main(String[] args) {
        int[] arr = new int[] {6, 5, 4, 3, 2, 1};
        System.out.println("--Unsorted array--");
        printArray(arr);
        HeapSort.sortArr(arr);
        System.out.println("--Sorted array--");
        printArray(arr);
    }

    private static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            System.out.print(" ");
        }
        System.out.print('\n');
    }
}