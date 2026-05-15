import edu.vladimir.primesocket.services.PrimeChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimeCheckerTest {

    @Test
    void testIsPrimeLogic() {
        assertTrue(PrimeChecker.isPrime(2));
        assertTrue(PrimeChecker.isPrime(3));
        assertTrue(PrimeChecker.isPrime(7));
        assertTrue(PrimeChecker.isPrime(13));
        assertTrue(PrimeChecker.isPrime(20165149));

        assertFalse(PrimeChecker.isPrime(4));
        assertFalse(PrimeChecker.isPrime(6));
        assertFalse(PrimeChecker.isPrime(9));
        assertFalse(PrimeChecker.isPrime(1));
        assertFalse(PrimeChecker.isPrime(-5));
    }

    @Test
    void testCheckArrayWithNonPrime() {
        int[] input = {6, 8, 7, 13, 5, 9, 4};
        assertTrue(PrimeChecker.checkArray(input));
    }

    @Test
    void testCheckArrayAllPrimes() {
        int[] input = {
                20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149,
                6998051, 6998053
        };

        assertFalse(PrimeChecker.checkArray(input));
    }
}