import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import vanisimov.creditbook.consts.EdLevel;
import vanisimov.creditbook.consts.Mark;
import vanisimov.creditbook.consts.Num;
import vanisimov.creditbook.consts.Subject;
import vanisimov.creditbook.implementation.*;

public class CreditBookTest {

    private Student student;
    private Curriculum curriculum;
    private CreditBook creditBook;

    @BeforeEach
    void setUp() {
        student = new Student("Иванов И.И.", "ФИТ-2101", EdLevel.FOURTH);
        curriculum = new Curriculum();
        creditBook = new CreditBook(student, curriculum);
    }

    private Semester createFullSemester(Num num, Mark examMark, Mark diffCreditMark) {
        Semester semester = new Semester(num);
        SemesterPlan plan = curriculum.getPlanForSemester(num);

        for (Subject s : plan.getExams()) {
            semester.putExamInfo(s, examMark);
        }

        for (Subject s : plan.getDiffCredits()) {
            semester.putDiffCreditInfo(s, diffCreditMark);
        }

        for (Subject s : plan.getCredits()) {
            semester.putCreditInfo(s, Mark.CREDIT);
        }

        return semester;
    }

    @Test
    void testCalculateAverage_ExcellentMarks() {
        creditBook.addSemester(createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT));
        creditBook.addSemester(createFullSemester(Num.II, Mark.EXCELLENT, Mark.EXCELLENT));
        creditBook.addSemester(createFullSemester(Num.III, Mark.EXCELLENT, Mark.EXCELLENT));

        assertEquals(5.0, creditBook.calculateAverage(), 0.001);
    }

    @Test
    void testCalculateAverage_MixedMarks() {
        creditBook.addSemester(createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT));
        creditBook.addSemester(createFullSemester(Num.II, Mark.GOOD, Mark.GOOD));
        creditBook.addSemester(createFullSemester(Num.III, Mark.SATISFACTORY, Mark.SATISFACTORY));

        assertEquals(3.9523, creditBook.calculateAverage(), 0.0001);
    }

    @Test
    void testCalculateAverage_ExcludingCurrentSemester() {
        creditBook.addSemester(createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT));
        creditBook.addSemester(createFullSemester(Num.II, Mark.GOOD, Mark.GOOD));
        creditBook.addSemester(createFullSemester(Num.IV, Mark.SATISFACTORY, Mark.SATISFACTORY));

        assertEquals(4.5384, creditBook.calculateAverage(), 0.0001);
    }

    @Test
    void testCanTransferToBudget_Success() {
        Semester semI = createFullSemester(Num.I, Mark.SATISFACTORY, Mark.SATISFACTORY);
        Semester semII = createFullSemester(Num.II, Mark.EXCELLENT, Mark.SATISFACTORY);
        Semester semIII = createFullSemester(Num.III, Mark.GOOD, Mark.SATISFACTORY);

        creditBook.addSemester(semI);
        creditBook.addSemester(semII);
        creditBook.addSemester(semIII);

        assertTrue(creditBook.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudget_FailLastSessionExam() {
        Semester semI = createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT);
        Semester semII = createFullSemester(Num.II, Mark.EXCELLENT, Mark.EXCELLENT);

        Semester semIII = createFullSemester(Num.III, Mark.GOOD, Mark.EXCELLENT);
        semIII.putExamInfo(Subject.STATISTICS, Mark.SATISFACTORY);

        creditBook.addSemester(semI);
        creditBook.addSemester(semII);
        creditBook.addSemester(semIII);

        assertFalse(creditBook.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudget_FailPreLastSessionExam() {
        Semester semI = createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT);

        Semester semII = createFullSemester(Num.II, Mark.EXCELLENT, Mark.EXCELLENT);
        semII.putExamInfo(Subject.MATH, Mark.SATISFACTORY);

        Semester semIII = createFullSemester(Num.III, Mark.EXCELLENT, Mark.EXCELLENT);

        creditBook.addSemester(semI);
        creditBook.addSemester(semII);
        creditBook.addSemester(semIII);

        assertFalse(creditBook.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudget_DiffCreditSatisfactoryAllowed() {
        Semester semI = createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT);
        Semester semII = createFullSemester(Num.II, Mark.EXCELLENT, Mark.EXCELLENT);
        semII.putDiffCreditInfo(Subject.DECPROG, Mark.SATISFACTORY);
        Semester semIII = createFullSemester(Num.III, Mark.EXCELLENT, Mark.EXCELLENT);
        semIII.putDiffCreditInfo(Subject.OOP, Mark.SATISFACTORY);
        creditBook.addSemester(semI);
        creditBook.addSemester(semII);
        creditBook.addSemester(semIII);

        assertTrue(creditBook.canTransferToBudget());
    }

    @Test
    void testCanGetRedDiploma_ExcellentMarks() {
        creditBook.addSemester(createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT));
        creditBook.addSemester(createFullSemester(Num.II, Mark.EXCELLENT, Mark.EXCELLENT));
        creditBook.addSemester(createFullSemester(Num.III, Mark.EXCELLENT, Mark.EXCELLENT));

        assertTrue(creditBook.canGetRedDiploma(Mark.CREDIT));
    }

    @Test
    void testCanGetRedDiploma_Below75Percent() {
        creditBook.addSemester(createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT));
        creditBook.addSemester(createFullSemester(Num.II, Mark.GOOD, Mark.GOOD));
        creditBook.addSemester(createFullSemester(Num.III, Mark.GOOD, Mark.GOOD));

        assertFalse(creditBook.canGetRedDiploma(Mark.CREDIT));
    }

    @Test
    void testCanGetRedDiploma_HasSatisfactory() {
        creditBook.addSemester(createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT));
        creditBook.addSemester(createFullSemester(Num.II, Mark.EXCELLENT, Mark.EXCELLENT));
        Semester semIII = createFullSemester(Num.III, Mark.EXCELLENT, Mark.EXCELLENT);
        semIII.putExamInfo(Subject.COMPUTATIONS, Mark.SATISFACTORY);
        creditBook.addSemester(semIII);

        assertFalse(creditBook.canGetRedDiploma(Mark.CREDIT));
    }

    @Test
    void testCanGetRedDiploma_GraduatedRequiresVkrExcellent() {
        student.setEdLevel(EdLevel.GRADUATED);
        creditBook.addSemester(createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT));

        assertFalse(creditBook.canGetRedDiploma(Mark.GOOD));

        assertTrue(creditBook.canGetRedDiploma(Mark.EXCELLENT));
    }

    @Test
    void testCanGetIncreasedScholarship_Eligible() {
        creditBook.addSemester(createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT));
        creditBook.addSemester(createFullSemester(Num.II, Mark.EXCELLENT, Mark.EXCELLENT));
        creditBook.addSemester(createFullSemester(Num.III, Mark.EXCELLENT, Mark.EXCELLENT));

        assertTrue(creditBook.canGetIncreasedScholarship());
    }

    @Test
    void testCanGetIncreasedScholarship_HasSatisfactory() {
        creditBook.addSemester(createFullSemester(Num.I, Mark.EXCELLENT, Mark.EXCELLENT));
        Semester semII = createFullSemester(Num.II, Mark.EXCELLENT, Mark.EXCELLENT);
        semII.putExamInfo(Subject.MATH, Mark.SATISFACTORY);
        creditBook.addSemester(semII);

        assertFalse(creditBook.canGetIncreasedScholarship());
    }

    @Test
    void testCanGetIncreasedScholarship_Graduated() {
        student.setEdLevel(EdLevel.GRADUATED);

        assertFalse(creditBook.canGetIncreasedScholarship());
    }
}