package student;

import edu.taskchecker.vladimir.domain.Student;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestStudent {

    private static final String name     = "Анисимов Вовка";
    private static final String repoURL = "https://github.com/VlanAni/OOP";
    private static final String nickname = "VlanAni";

    @Nested
    class Construction {

        @Test
        void success() {
            assertDoesNotThrow(() ->
                    new Student(nickname, repoURL, nickname));
        }

        @Test
        void nullName() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Student(null, repoURL, nickname));
        }

        @Test
        void blankName() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Student("  ", repoURL, nickname));
        }

        @Test
        void nullRepoUrl() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Student(name, null, nickname));
        }

        @Test
        void blankRepoUrl() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Student(name, "  ", nickname));
        }

        @Test
        void nullNickname() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Student(name, repoURL, null));
        }

        @Test
        void blankNickname() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Student(name, repoURL, "  "));
        }
    }

    @Nested
    class Getters {

        private final Student student =
                new Student(name, repoURL, nickname);

        @Test
        void getName() {
            assertEquals(name, student.getName());
        }

        @Test
        void getRepoUrl() {
            assertEquals(repoURL, student.getRepoURL());
        }

        @Test
        void getNickname() {
            assertEquals(nickname, student.getNickname());
        }
    }
}
