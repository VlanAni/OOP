package checkassigment;

import edu.taskchecker.vladimir.domain.CheckAssignment;
import edu.taskchecker.vladimir.domain.Student;
import edu.taskchecker.vladimir.domain.TaskData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCheckAssigment {

    private static final String name     = "Анисимов Вовка";
    private static final String repoURL = "https://github.com/VlanAni/OOP";
    private static final String nickname = "VlanAni";

    private static final String id = "Task_2_2_1";
    private static final String taskName = "Snake";
    private static final int score = 1;
    private static final LocalDate softDeadline = LocalDate.of(2026, 3, 1);
    private static final LocalDate hardDeadline = LocalDate.of(2026, 4, 1);

    private static final List<Student> studentList = new ArrayList<>();
    private static final List<TaskData> taskDataList = new ArrayList<>();

    @BeforeAll
    public static void fillLists() {
        studentList.add(new Student(name, repoURL, nickname));
        taskDataList.add(new TaskData(id, taskName, score, softDeadline, hardDeadline));
    }

    @Nested
    class Construction {
        @Test
        void success() {
            assertDoesNotThrow(() ->
                    new CheckAssignment(studentList, taskDataList));
        }

        @Test
        void nullStudentList() {
            assertThrows(IllegalArgumentException.class, () ->
                    new CheckAssignment(null, taskDataList));
        }

        @Test
        void nullTaskDataList() {
            assertThrows(IllegalArgumentException.class, () ->
                    new CheckAssignment(studentList, null));
        }

        @Test
        void nullEverything() {
            assertThrows(IllegalArgumentException.class, () ->
                    new CheckAssignment(null, null));
        }
    }

    @Nested
    class Getters {
        private final CheckAssignment checkAssignment = new CheckAssignment(studentList, taskDataList);

        @Test
        void getStudentList() {
            assertNotNull(checkAssignment.getStudents());
        }

        @Test
        void getTaskDataList() {
            assertNotNull(checkAssignment.getTasks());
        }

        @Test
        void testSafety() {
            assertThrows(Exception.class, () -> checkAssignment.getStudents().add(new Student(name, repoURL, nickname)));

            assertThrows(Exception.class, () -> checkAssignment.getTasks().add(new TaskData(id, taskName, score, softDeadline, hardDeadline)));
        }
    }
}
