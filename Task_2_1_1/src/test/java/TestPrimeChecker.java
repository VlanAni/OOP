import org.junit.jupiter.api.Test;
import vanisimov.task2_1_1.primechecker.PrimeChecker;

public class TestPrimeChecker {

    @Test
    void testZeroOne() {

        assert (!PrimeChecker.isPrime(0));

        assert (!PrimeChecker.isPrime(1));

    }

    @Test
    void testLittleNumbers() {
        for (int i : new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23}) {

            assert (PrimeChecker.isPrime(i));

        }
    }

    @Test
    void testLittleNumbers2() {
        for (int i : new int[] {4, 8, 1000000, 20002224}) {

            assert (!PrimeChecker.isPrime(i));

        }
    }
}
