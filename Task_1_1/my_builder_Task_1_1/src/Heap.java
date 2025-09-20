class Heap {

    private int[] nodes;
    private int size;

    public void sort(int[] array) {
        int arrsize = array.length;
        int minval;
        this.buildHeap(array, arrsize);
        for (int i = 0; i < arrsize; i++) {
            minval = this.extractMin();
            array[i] = minval;
        }
    }

    private void buildHeap(int[] array, int arrsize) {
        this.nodes = new int[arrsize];
        for (int i = 0; i < arrsize; i++) {
            this.addNode(array[i]);
        }
    }

    private int extractMin() {
        int minvalue = this.nodes[0];
        swapInHeap(0, this.size - 1);
        this.size--;
        int childtoswap = 0;
        int curridx = 0;
        int lcidx;
        int rcidx;
        while (curridx * 2 + 1 < this.size) {
            lcidx = curridx * 2 + 1;
            rcidx = curridx * 2 + 2;
            if (this.nodes[lcidx] < this.nodes[curridx]) {
                childtoswap = lcidx;
            }
            if (rcidx < size & nodes[rcidx] < nodes[curridx] & nodes[lcidx] > nodes[rcidx]) {
                childtoswap = rcidx;
            }
            if (curridx == childtoswap) {
                break;
            }
            swapInHeap(curridx, childtoswap);
            curridx = childtoswap;
        }
        return minvalue;
    }

    private void addNode(int number) {
        this.nodes[size] = number;
        int curridx = size;
        this.size++;
        while (curridx > 0 & nodes[curridx] < nodes[(curridx - 1) / 2]) {
            swapInHeap(curridx, (curridx - 1) / 2);
            curridx = (curridx - 1) / 2;
        }
    }

    private void swapInHeap(int idx1, int idx2) {
        int t = this.nodes[idx1];
        this.nodes[idx1] = this.nodes[idx2];
        this.nodes[idx2] = t;
    }
}