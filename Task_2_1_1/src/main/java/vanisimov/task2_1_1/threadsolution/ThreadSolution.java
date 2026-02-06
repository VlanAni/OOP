package vanisimov.task2_1_1.threadsolution;

import vanisimov.task2_1_1.primechecker.PrimeChecker;
import vanisimov.task2_1_1.solutioninterface.Solution;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadSolution implements Solution {

    private final int threadAmount;

    public ThreadSolution(int threadAmount) {
        this.threadAmount = threadAmount;
    }

    public boolean CheckArray(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return false;
        }

        if (this.threadAmount <= 0) {
            return false;
        }

        int actualThreads = Math.min(this.threadAmount, numbers.length);
        Thread[] threads = new Thread[actualThreads];
        AtomicBoolean found = new AtomicBoolean(false);
        int chunkSize = numbers.length / actualThreads;
        for (int thrIdx = 0; thrIdx < actualThreads; thrIdx++) {
            final int start = thrIdx * chunkSize;
            final int end = (thrIdx == actualThreads - 1) ? numbers.length : start + chunkSize;
            threads[thrIdx] = new Thread(() ->
            {
                if (found.get()) {
                    return;
                }
                for (int i = start; i < end; ++i) {
                    if (!PrimeChecker.isPrime(numbers[i])) {
                        found.set(true);
                        return;
                    }
                }
            });
            threads[thrIdx].start();
        }

        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted!");

        }

        return found.get();
    }

}