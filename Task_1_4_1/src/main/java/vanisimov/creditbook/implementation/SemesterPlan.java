package vanisimov.creditbook.implementation;

import java.util.List;
import vanisimov.creditbook.consts.Subject;

public class SemesterPlan {

    private final List<Subject> credits;
    private final List<Subject> diffCredits;
    private final List<Subject> exams;

    public SemesterPlan(List<Subject> credits, List<Subject> diffCredits, List<Subject> exams) {
        this.credits = credits;
        this.diffCredits = diffCredits;
        this.exams = exams;
    }

    public List<Subject> getCredits() { return credits; }
    public List<Subject> getDiffCredits() { return diffCredits; }
    public List<Subject> getExams() { return exams; }
}
