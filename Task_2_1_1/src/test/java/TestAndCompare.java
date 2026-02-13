import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import vanisimov.task2_1_1.parallelstreamsolution.ParallelStreamSolution;
import vanisimov.task2_1_1.sequentialsolution.SequentialSolution;
import vanisimov.task2_1_1.threadsolution.ThreadSolution;
import vanisimov.task2_1_1.solutioninterface.Solution;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAndCompare {

    private static int[] data;
    private static final int SIZE = 100_000;

    @BeforeAll
    static void setup() {
        data = new int[SIZE];
        Arrays.fill(data, 1000000007);
        data[SIZE - 1] = 4;
    }

    @Test
    @Order(1)
    void testSequential() {
        measureAndAssert("Sequential", new SequentialSolution());
    }

    @Test
    @Order(2)
    void testThreads() {
        int maxCores = Runtime.getRuntime().availableProcessors();
        for (int i = 1; i <= maxCores; i++) {
            measureAndAssert("Threads (" + i + ")", new ThreadSolution(i));
        }
    }

    @Test
    @Order(3)
    void testParallelStream() {
        measureAndAssert("ParallelStream", new ParallelStreamSolution());
    }

    private void measureAndAssert(String label, Solution solution) {
        long start = System.nanoTime();
        boolean hasNonPrime = solution.CheckArray(data);
        long end = System.nanoTime();

        double durationMs = (end - start) / 1_000_000.0;

        assertTrue(hasNonPrime, label + " wrong result!");

        System.out.printf("%-20s | time: %.2f ms%n", label, durationMs);
    }
}
