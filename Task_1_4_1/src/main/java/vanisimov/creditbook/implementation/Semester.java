package vanisimov.creditbook.implementation;

import java.util.EnumMap;
import java.util.Map;
import vanisimov.creditbook.consts.Mark;
import vanisimov.creditbook.consts.Num;
import vanisimov.creditbook.consts.Subject;

/**
 * Semester contains an information about a semester.
 */
public class Semester {

    private Map<Subject, Mark> credits;
    private Map<Subject, Mark> diffCredits;
    private Map<Subject, Mark> exams;
    private Num semstrNum;

    public Semester(Num semstrNum) {
        this.semstrNum = semstrNum;
        this.credits = new EnumMap<>(Subject.class);
        this.diffCredits = new EnumMap<>(Subject.class);
        this.exams = new EnumMap<>(Subject.class);
    }

    public Mark getCreditInfo(Subject sub) {
        return this.credits.get(sub);
    }

    public Mark getDiffCreditInfo(Subject sub) {
        return this.diffCredits.get(sub);
    }

    public Mark getExamInfo(Subject sub) {
        return this.exams.get(sub);
    }

    public void putCreditInfo(Subject sub, Mark mark) {
        this.credits.put(sub, mark);
    }

    public void putDiffCreditInfo(Subject sub, Mark mark) {
        this.diffCredits.put(sub, mark);
    }

    public void putExamInfo(Subject sub, Mark mark) {
        this.exams.put(sub, mark);
    }

    public Num getSemstrNum() {
        return this.semstrNum;
    }

    public void setSemstrNum(Num newNum) {
        this.semstrNum = newNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--СЕМЕСТР: ").append(this.semstrNum.name()).append(" --\n");
        addInfo(sb);
        return sb.toString();
    }

    private void addInfo(StringBuilder sb) {
        sb.append("ЗАЧЁТЫ\n");
        for (Subject sub : this.credits.keySet()) {
            sb.append(sub.getSubName());
            sb.append(": ");
            sb.append(this.credits.get(sub).getValue());
            sb.append('\n');
        }
        sb.append("ДИФ-ЗАЧЁТЫ\n");
        for (Subject sub : this.diffCredits.keySet()) {
            sb.append(sub.getSubName());
            sb.append(": ");
            sb.append(this.diffCredits.get(sub).getValue());
            sb.append('\n');
        }
        sb.append("ЭКЗАМЕНЫ\n");
        for (Subject sub : this.exams.keySet()) {
            sb.append(sub.getSubName());
            sb.append(": ");
            sb.append(this.exams.get(sub).getValue());
            sb.append('\n');
        }
    }
}
