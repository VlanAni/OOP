package vanisimov.graphdevkit.customarray;

import java.util.Arrays;

public class DynamicArray {

    private int size;
    private int capacity;
    private int[] array;

    public DynamicArray(int size) {
        this.size = size;
        if (size == 0) {
            this.capacity = 10;
        } else {
            this.capacity = this.size * 2;
        }
        this.array = new int[this.capacity];
    }

    public void addElem(int val) {
        this.size++;
        if (this.size > this.capacity) {
            int[] newArr = Arrays.copyOf(this.array, this.capacity * 2);
            newArr[this.size - 1] = val;
            this.array = newArr;
            this.capacity *= 2;
        } else {
            this.array[this.size - 1] = val;
        }
    }

    public void rmElem(int idx) {
        if (idx >= this.size)
            return;
        if (idx < 0)
            return;
        int[] newArr = new int[this.capacity];
        int newarrPointer = 0;
        for (int i = 0; i < this.size; ++i) {
            if (i != idx) {
                newArr[newarrPointer] = this.array[i];
                newarrPointer++;
            }
        }
        this.size -= 1;
        this.array = newArr;
    }

    public int getValue(int idx) {
        if (idx < 0 || idx >= this.size) {
            return -1;
        } else {
            return this.array[idx];
        }
    }

    public void setValue(int idx, int value) {
        if (idx < 0 || idx >= this.size) {
            return;
        } else {
            this.array[idx] = value;
        }
    }

    public int getSize() {
        return this.size;
    }

}