package vanisimov.task2_1_1.parallelstreamsolution;

import vanisimov.task2_1_1.primechecker.PrimeChecker;
import vanisimov.task2_1_1.solutioninterface.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ParallelStreamSolution implements Solution {

    public ParallelStreamSolution() {}

    @Override
    public boolean CheckArray(int[] numbers) {
        Collection<Integer> collection = Arrays.stream(numbers)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        return collection.parallelStream().anyMatch(n -> !PrimeChecker.isPrime(n));
    }
}