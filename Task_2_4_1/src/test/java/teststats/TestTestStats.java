package teststats;

import edu.taskchecker.vladimir.domain.TestStats;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTestStats {
    private final int passedTests = 5;
    private final int failedTests = 4;
    private final int ignoredTests = 3;

    @Nested
    class Construction {
        @Test
        void success() {
            assertDoesNotThrow(() ->
                    new TestStats(passedTests, failedTests, ignoredTests));
        }

        @Test
        void nullPassedTests() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TestStats(-1, failedTests, ignoredTests));
        }

        @Test
        void nullFailedTests() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TestStats(passedTests, -1, ignoredTests));
        }

        @Test
        void nullIgnoredTests() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TestStats(passedTests, failedTests, -1));
        }

        @Test
        void nullEverything() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TestStats(-1, -1, -1));
        }
    }

    @Nested
    class Getters {
        private final TestStats stat = new TestStats(passedTests, failedTests, ignoredTests);

        @Test
        void getPassedTests() {
            assertEquals(stat.getPassedTests(), passedTests);
        }

        @Test
        void getFailedTests() {
            assertEquals(stat.getFailedTests(), failedTests);
        }

        @Test
        void getIgnoredTests() {
            assertEquals(stat.getIgnoredTests(), ignoredTests);
        }
    }
}
