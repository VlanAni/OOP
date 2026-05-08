package gradingconfig;

import edu.taskchecker.vladimir.domain.GradingConfig;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestGradingConfig {

    private static final int excellent = 5;
    private static final int good = 4;
    private static final int pass = 3;
    private static final double penalty = 0.5;
    private static final int timeout = 30;

    @Nested
    class Construction {
        @Test
        void success() {
            assertDoesNotThrow(() ->
                    new GradingConfig(excellent, good, pass, penalty, timeout, Map.of()));
        }

        @Test
        void defaultConfig() {
            assertDoesNotThrow(GradingConfig::defaultConfig);
        }

        @Test
        void nullBonusPoints() {
            assertThrows(IllegalArgumentException.class, () ->
                    new GradingConfig(excellent, good, pass, penalty, timeout, null));
        }

        @Test
        void excellentNotGreaterThanGood() {
            assertThrows(IllegalArgumentException.class, () ->
                    new GradingConfig(70, 70, pass, penalty, timeout, Map.of()));
        }

        @Test
        void goodNotGreaterThanPass() {
            assertThrows(IllegalArgumentException.class, () ->
                    new GradingConfig(excellent, 50, 50, penalty, timeout, Map.of()));
        }

        @Test
        void negativePass() {
            assertThrows(IllegalArgumentException.class, () ->
                    new GradingConfig(excellent, good, -1, penalty, timeout, Map.of()));
        }

        @Test
        void penaltyGreaterThanOne() {
            assertThrows(IllegalArgumentException.class, () ->
                    new GradingConfig(excellent, good, pass, 1.1, timeout, Map.of()));
        }

        @Test
        void negativePenalty() {
            assertThrows(IllegalArgumentException.class, () ->
                    new GradingConfig(excellent, good, pass, -0.1, timeout, Map.of()));
        }

        @Test
        void zeroTimeout() {
            assertThrows(IllegalArgumentException.class, () ->
                    new GradingConfig(excellent, good, pass, penalty, 0, Map.of()));
        }
    }

    @Nested
    class Getters {
        private final GradingConfig config =
                new GradingConfig(excellent, good, pass, penalty, timeout, Map.of());

        @Test
        void getExcellentThreshold() {
            assertEquals(excellent, config.getExcellentThreshold());
        }

        @Test
        void getGoodThreshold() {
            assertEquals(good, config.getGoodThreshold());
        }

        @Test
        void getPassThreshold() {
            assertEquals(pass, config.getPassThreshold());
        }

        @Test
        void getSoftDeadlinePenalty() {
            assertEquals(penalty, config.getSoftDeadlinePenalty());
        }

        @Test
        void getTestTimeoutSeconds() {
            assertEquals(timeout, config.getTestTimeoutSeconds());
        }
    }

    @Nested
    class GetBonusFor {

        @Test
        void returnsBonus() {
            GradingConfig config = new GradingConfig(
                    excellent, good, pass, penalty, timeout,
                    Map.of("VlanAni", Map.of("Task_2_2_1", 2))
            );
            assertEquals(2, config.getBonusFor("VlanAni", "Task_2_2_1"));
        }

        @Test
        void returnsZeroForUnknownStudent() {
            GradingConfig config = new GradingConfig(
                    excellent, good, pass, penalty, timeout, Map.of()
            );
            assertEquals(0, config.getBonusFor("unknown", "Task_2_2_1"));
        }

        @Test
        void returnsZeroForUnknownTask() {
            GradingConfig config = new GradingConfig(
                    excellent, good, pass, penalty, timeout,
                    Map.of("VlanAni", Map.of("Task_2_1_1", 2))
            );
            assertEquals(0, config.getBonusFor("VlanAni", "Task_2_2_1"));
        }
    }
}
