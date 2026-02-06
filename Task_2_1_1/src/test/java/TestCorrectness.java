import org.junit.jupiter.api.Test;
import vanisimov.task2_1_1.parallelstreamsolution.ParallelStreamSolution;
import vanisimov.task2_1_1.sequentialsolution.SequentialSolution;
import vanisimov.task2_1_1.solutioninterface.Solution;
import vanisimov.task2_1_1.threadsolution.ThreadSolution;

public class TestCorrectness {

    private final int[] easy = {6, 8, 7, 13, 5, 9, 4};
    private final int[] hard = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
    Solution sol;

    @Test
    void testSeqSolution() {
        sol = new SequentialSolution();

        assert (sol.CheckArray(easy));

        assert (!sol.CheckArray(hard));
    }

    @Test
    void testThreadSolution() {
        sol = new ThreadSolution(2);

        assert (sol.CheckArray(easy));

        assert (!sol.CheckArray(hard));
    }

    @Test
    void testParallelSolution() {
        sol = new ParallelStreamSolution();

        assert (sol.CheckArray(easy));

        assert (!sol.CheckArray(hard));
    }
}
