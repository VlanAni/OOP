package vanisimov.task2_1_1.sequentialsolution;

import java.util.Arrays;

import vanisimov.task2_1_1.primechecker.PrimeChecker;
import vanisimov.task2_1_1.solutioninterface.Solution;

public class SequentialSolution implements Solution {

    public SequentialSolution() {}

    public boolean CheckArray(int[] numbers) {
        return Arrays.stream(numbers).anyMatch(n -> !PrimeChecker.isPrime(n));
    }

}