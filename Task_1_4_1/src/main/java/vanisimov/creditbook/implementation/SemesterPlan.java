package vanisimov.creditbook.implementation;

import java.util.List;

import lombok.Getter;
import vanisimov.creditbook.consts.Subject;

@Getter
public class SemesterPlan {

    private final List<Subject> credits;
    private final List<Subject> diffCredits;
    private final List<Subject> exams;

    public SemesterPlan(List<Subject> credits, List<Subject> diffCredits, List<Subject> exams) {
        this.credits = credits;
        this.diffCredits = diffCredits;
        this.exams = exams;
    }
}
