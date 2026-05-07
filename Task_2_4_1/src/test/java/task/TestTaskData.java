package task;

import edu.taskchecker.vladimir.domain.TaskData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestTaskData {

    private static final String id = "Task_2_2_1";
    private static final String name = "Snake";
    private static final int score = 1;
    private static final LocalDate softDeadline = LocalDate.of(2026, 3, 1);
    private static final LocalDate hardDeadline = LocalDate.of(2026, 4, 1);

    @Nested
    class Constructor {
        @Test
        void success() {
            assertDoesNotThrow(() ->
                    new TaskData(id, name, score, softDeadline, hardDeadline));
        }

        @Test
        void nullId() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskData(null, name, score, softDeadline, hardDeadline));
        }

        @Test
        void blankId() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskData("  ", name, score, softDeadline, hardDeadline));
        }

        @Test
        void nullName() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskData(id, null, score, softDeadline, hardDeadline));
        }

        @Test
        void blankName() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskData(id, "  ", score, softDeadline, hardDeadline));
        }

        @Test
        void zeroMaxScore() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskData(id, name, 0, softDeadline, hardDeadline));
        }

        @Test
        void negativeMaxScore() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskData(id, name, -1, softDeadline, hardDeadline));
        }

        @Test
        void nullSoftDeadline() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskData(id, name, score, null, hardDeadline));
        }

        @Test
        void nullHardDeadline() {
            assertThrows(IllegalArgumentException.class, () ->
                    new TaskData(id, name, score, softDeadline, null));
        }
    }

    @Nested
    class Getters {

        private final TaskData task =
                new TaskData(id, name, score, softDeadline, hardDeadline);

        @Test
        void getId() {
            assertEquals(id, task.getId());
        }

        @Test
        void getName() {
            assertEquals(name, task.getName());
        }

        @Test
        void getMaxScore() {
            assertEquals(score, task.getMaxScore());
        }

        @Test
        void getSoftDeadline() {
            assertEquals(softDeadline, task.getSoftDeadline());
        }

        @Test
        void getHardDeadline() {
            assertEquals(hardDeadline, task.getHardDeadline());
        }
    }
}
