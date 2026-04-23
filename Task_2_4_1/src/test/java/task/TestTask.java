package task;

import edu.taskchecker.vladimir.domain.TaskData;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TestTask {
    @Test
    void testSafety() {
        try {
            TaskData taskData = new TaskData("id", "dosapk", 1, null, LocalDate.now());
        } catch (Exception e) {
            assert (true);

            return;
        }

        assert (false);
    }

    @Test
    void testBlank() {
        try {
            TaskData taskData = new TaskData("", " ", 1, LocalDate.now(), LocalDate.now());
        } catch (Exception e) {
            assert (true);

            return;
        }

        assert (false);
    }

    @Test
    void testNegativeScore() {
        try {
            TaskData taskData = new TaskData("id", "id", -1, LocalDate.now(), LocalDate.now());
        } catch (Exception e) {
            assert (true);

            return;
        }

        assert (false);
    };
}
