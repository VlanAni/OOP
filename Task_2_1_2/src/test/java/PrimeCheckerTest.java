import edu.vladimir.primesocket.exceptions.NotPrimeNumberException;
import edu.vladimir.primesocket.services.CachingPrimeChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimeCheckerTest {
    private CachingPrimeChecker primeChecker;

    @BeforeEach
    void initCachingChecker() {
        this.primeChecker = new CachingPrimeChecker();
    }

    @Test
    void testIsPrimeLogic() {
        assertDoesNotThrow(() -> {
            primeChecker.put(2);
            primeChecker.put(3);
            primeChecker.put(7);
            primeChecker.put(13);
            primeChecker.put(20165149);
        });

        assertThrows(NotPrimeNumberException.class, () -> primeChecker.put(4));
        assertThrows(NotPrimeNumberException.class, () -> primeChecker.put(6));
        assertThrows(NotPrimeNumberException.class, () -> primeChecker.put(9));
        assertThrows(NotPrimeNumberException.class, () -> primeChecker.put(1));
        assertThrows(NotPrimeNumberException.class, () -> primeChecker.put(-5));
    }

    @Test
    void testCheckArrayWithNonPrime() {
        int[] input = {6, 8, 7, 13, 5, 9, 4};
        assertTrue(primeChecker.isArrayHasNonPrime(input));
    }

    @Test
    void testCheckArrayAllPrimes() {
        int[] input = {
                20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149,
                6998051, 6998053
        };

        assertFalse(primeChecker.isArrayHasNonPrime(input));
    }
}