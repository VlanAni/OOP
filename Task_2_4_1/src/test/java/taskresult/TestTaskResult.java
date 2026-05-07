package taskresult;

import edu.taskchecker.vladimir.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestTaskResult {

    private static final LocalDate softDeadline = LocalDate.of(2026, 3, 1);
    private static final LocalDate hardDeadline = LocalDate.of(2026, 4, 1);
    private static final LocalDate beforeSoft = LocalDate.of(2026, 2, 1);
    private static final LocalDate afterSoft = LocalDate.of(2026, 3, 15);
    private static final LocalDate afterHard = LocalDate.of(2026, 5, 1);

    private TaskData task;
    private Student student;
    private GradingConfig config;
    private TestStats passingTests;
    private TestStats      failingTests;
    private TestStats      emptyTests;

    @BeforeEach
    void setUp() {
        task = new TaskData("Task_2_2_1", "Snake", 5, softDeadline, hardDeadline);
        student = new Student("Владимир", "https://github.com/VlanAni/OOP", "VlanAni");
        config = new GradingConfig(5, 4, 3, 0.5, 30, Map.of());
        passingTests = new TestStats(5, 0, 0);
        failingTests = new TestStats(3, 2, 0);
        emptyTests   = new TestStats(0, 0, 0);
    }

    @Nested
    class Construction {

        @Test
        void success() {
            assertDoesNotThrow(() ->
                    new TaskResult(task, student, true, true,
                            passingTests, beforeSoft, config));
        }

        @Test
        void nullTask() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskResult(null, student, true, true,
                            passingTests, beforeSoft, config));
        }

        @Test
        void nullStudent() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskResult(task, null, true, true,
                            passingTests, beforeSoft, config));
        }

        @Test
        void nullTestStat() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskResult(task, student, true, true,
                            null, beforeSoft, config));
        }

        @Test
        void nullLastCommitDate() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskResult(task, student, true, true,
                            passingTests, null, config));
        }

        @Test
        void nullGradingConfig() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskResult(task, student, true, true,
                            passingTests, beforeSoft, null));
        }
    }

    @Nested
    class Getters {

        private TaskResult result;

        @BeforeEach
        void setUp() {
            result = new TaskResult(task, student, true, true,
                    passingTests, beforeSoft, config);
        }

        @Test
        void getTask()           { assertEquals(task, result.getTask()); }

        @Test
        void getStudent()        { assertEquals(student, result.getStudent()); }

        @Test
        void isSuccessBuild()    { assertTrue(result.isSuccessBuild()); }

        @Test
        void isMatchStyle()      { assertTrue(result.isMatchStyle()); }

        @Test
        void getTestStat()       { assertEquals(passingTests, result.getTestStat()); }

        @Test
        void getLastCommitDate() { assertEquals(beforeSoft, result.getLastCommitDate()); }

        @Test
        void getScore()          { assertTrue(result.getScore() >= 0); }
    }

    @Nested
    class ScoreCalculation {

        @Test
        void zeroIfBuildFailed() {
            TaskResult result = new TaskResult(task, student, false, false,
                    emptyTests, beforeSoft, config);
            assertEquals(0, result.getScore());
        }

        @Test
        void zeroIfTestsFailed() {
            TaskResult result = new TaskResult(task, student, true, true,
                    failingTests, beforeSoft, config);
            assertEquals(0, result.getScore());
        }

        @Test
        void zeroIfAfterHardDeadline() {
            TaskResult result = new TaskResult(task, student, true, true,
                    passingTests, afterHard, config);
            assertEquals(0, result.getScore());
        }

        @Test
        void fullScoreBeforeSoftDeadline() {
            TaskResult result = new TaskResult(task, student, true, true,
                    passingTests, beforeSoft, config);
            assertEquals(task.getMaxScore(), result.getScore());
        }

        @Test
        void halfScoreAfterSoftDeadline() {
            TaskResult result = new TaskResult(task, student, true, true,
                    passingTests, afterSoft, config);
            assertEquals(task.getMaxScore() * 0.5, result.getScore());
        }

        @Test
        void penaltyFromConfig() {
            GradingConfig strictConfig =
                    new GradingConfig(5, 4, 3, 0.25, 30, Map.of());
            TaskResult result = new TaskResult(task, student, true, true,
                    passingTests, afterSoft, strictConfig);
            assertEquals(task.getMaxScore() * 0.25, result.getScore());
        }

        @Test
        void bonusPointsAdded() {
            GradingConfig bonusConfig = new GradingConfig(
                    5, 4, 3, 0.5, 30,
                    Map.of("Владимир", Map.of("Task_2_2_1", 2))
            );
            TaskResult result = new TaskResult(task, student, true, true,
                    passingTests, beforeSoft, bonusConfig);
            assertEquals(task.getMaxScore() + 2, result.getScore());
        }

        @Test
        void noBonusForOtherStudent() {
            GradingConfig bonusConfig = new GradingConfig(
                    5, 4, 3, 0.5, 30,
                    Map.of("НеВладимир", Map.of("Task_2_2_1", 2))
            );
            TaskResult result = new TaskResult(task, student, true, true,
                    passingTests, beforeSoft, bonusConfig);
            assertEquals(task.getMaxScore(), result.getScore());
        }
    }
}