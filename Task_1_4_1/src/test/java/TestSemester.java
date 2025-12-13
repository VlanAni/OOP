import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vanisimov.creditbook.consts.Mark;
import vanisimov.creditbook.consts.SemesterNum;
import vanisimov.creditbook.consts.Subject;
import vanisimov.creditbook.implementation.Semester;

public class TestSemester {

    private static Semester sem;
    private static final SemesterNum num = SemesterNum.III;

    @BeforeEach
    void prepareSem() {
        TestSemester.sem = new Semester(num);
    }

    @Test
    void testGetSetNum() {
        TestSemester.sem.setSemesterNum(SemesterNum.V);

        assert (TestSemester.sem.getSemesterNum().equals(SemesterNum.V));

        assert (!TestSemester.sem.getSemesterNum().equals(TestSemester.num));
    }

    @Test
    void testGetPutDiffCredit() {
        TestSemester.sem.putDiffCreditInfo(Subject.IMPPROG, Mark.EXCELLENT);

        assert (TestSemester.sem.getDiffCreditInfo(Subject.IMPPROG) != null);

        assert (TestSemester.sem.getDiffCreditInfo(Subject.IMPPROG).equals(Mark.EXCELLENT));
    }

    @Test
    void testGetPutExams() {
        TestSemester.sem.putExamInfo(Subject.OS, Mark.GOOD);

        assert (TestSemester.sem.getExamInfo(Subject.OS) != null);

        assert (TestSemester.sem.getExamInfo(Subject.OS).equals(Mark.GOOD));
    }

    @Test
    void testGetPutCredit() {
        TestSemester.sem.putCreditInfo(Subject.LAW, Mark.CREDIT);

        assert (TestSemester.sem.getCreditInfo(Subject.LAW) != null);

        assert (TestSemester.sem.getCreditInfo(Subject.LAW).equals(Mark.CREDIT));
    }

    @Test
    void testToString() {
        TestSemester.sem.putCreditInfo(Subject.LAW, Mark.CREDIT);
        TestSemester.sem.putDiffCreditInfo(Subject.IMPPROG, Mark.EXCELLENT);
        TestSemester.sem.putExamInfo(Subject.OS, Mark.GOOD);

        String semInfo = TestSemester.sem.toString();

        assert (semInfo.contains("ЗАЧЁТЫ") &&
                semInfo.contains("ДИФ-ЗАЧЁТЫ") &&
                semInfo.contains("ЭКЗАМЕНЫ"));

        assert (semInfo.contains(TestSemester.sem.getSemesterNum().name()));

        assert (semInfo.contains(Subject.LAW.getSubName() + ": " + Mark.CREDIT.getValue()));

        assert (semInfo.contains(Subject.IMPPROG.getSubName() + ": " + Mark.EXCELLENT.getValue()));

        assert (semInfo.contains(Subject.OS.getSubName() + ": " + Mark.GOOD.getValue()));
    }

}
