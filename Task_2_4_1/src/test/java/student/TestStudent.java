package student;

import edu.taskchecker.vladimir.domain.Student;
import org.junit.jupiter.api.Test;

public class TestStudent {
    @Test
    void testSafety() {
        try {
            Student student = new Student(null, null, null);
        } catch (Exception e) {
            assert (true);

            return;
        }

        assert (false);
    }

    @Test
    void testBlank() {
        try {
            Student student = new Student("", " ", "   ");
        } catch (Exception e) {
            assert (true);

            return;
        }

        assert (false);
    }

    @Test
    void testGetters() {
        Student student = new Student("студент", "rep", "vlanani");

        assert (student.getName().equals("студент"));

        assert (student.getRepoURL().equals("rep"));

        assert (student.getNickname().equals("vlanani"));
    }
}
