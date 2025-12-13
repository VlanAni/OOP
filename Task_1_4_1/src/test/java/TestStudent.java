import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vanisimov.creditbook.consts.EdLevel;
import vanisimov.creditbook.implementation.Student;

public class TestStudent {

    private static Student student;
    private static final String name = "Анисимов Владимир Сергеевич";
    private static final String group = "24214";
    private static final EdLevel edLevel = EdLevel.GRADUATED;
    private static final boolean isBudget = true;

    @BeforeEach
    void prepareStudent() {
        TestStudent.student = new Student(TestStudent.name,
                TestStudent.group, TestStudent.edLevel, TestStudent.isBudget);
    }

    @Test
    void testGetSetName() {
        TestStudent.student.setFullName("Дилер");

        assert (!TestStudent.student.getFullName().equals(TestStudent.name));

        assert (TestStudent.student.getFullName().equals("Дилер"));
    }

    @Test
    void testGetSetGroup() {
        TestStudent.student.setGroup("24215");

        assert (!TestStudent.student.getGroup().equals(TestStudent.group));

        assert (TestStudent.student.getGroup().equals("24215"));
    }

    @Test
    void testGetSetEdLevel() {
        TestStudent.student.setEdLevel(EdLevel.FOURTH);

        assert (!TestStudent.student.getEdLevel().equals(TestStudent.edLevel));

        assert (TestStudent.student.getEdLevel().equals(EdLevel.FOURTH));
    }

    @Test
    void testGetSetIsBudget() {
        TestStudent.student.setBudget(false);

        assert (!TestStudent.student.isBudget());
    }
}
