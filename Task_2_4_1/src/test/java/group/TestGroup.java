package group;

import edu.taskchecker.vladimir.domain.Group;
import edu.taskchecker.vladimir.domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestGroup {

    private static final String name     = "Анисимов Вовка";
    private static final String repoURL = "https://github.com/VlanAni/OOP";
    private static final String nickname = "VlanAni";

    private static final List<Student> studentList = new ArrayList<>();

    @BeforeAll
    public static void fillLists() {
        studentList.add(new Student(name, repoURL, nickname));
    }

    @Nested
    class Construction {
        @Test
        void success() {
            assertDoesNotThrow(() ->
                    new Group("24214", studentList));
        }

        @Test
        void nullName() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Group(null, studentList));
        }

        @Test
        void nullStudents() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Group("24214", null));
        }

        @Test
        void nullEverything() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Group(null, null));
        }
    }

    @Nested
    class Getters {
        private final Group group = new Group("24214", studentList);

        @Test
        void getStudentList() {
            assertNotNull(group.getStudents());
        }

        @Test
        void getName() {
            assertNotNull(group.getName());

            assertEquals("24214", group.getName());
        }

        @Test
        void testSafety() {
            assertThrows(Exception.class, () -> group.getStudents().add(new Student(name, repoURL, nickname)));
        }
    }

}
