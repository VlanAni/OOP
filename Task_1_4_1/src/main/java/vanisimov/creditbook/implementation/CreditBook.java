package vanisimov.creditbook.implementation;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import vanisimov.creditbook.consts.EdLevel;
import vanisimov.creditbook.consts.Mark;
import vanisimov.creditbook.consts.Subject;

public class CreditBook {

    private final Student student;
    private final Curriculum curriculum;
    private final List<Semester> semesters;

    public CreditBook(Student student, Curriculum curriculum) {
        this.student = student;
        this.curriculum = curriculum;
        this.semesters = new ArrayList<>();
    }

    public void addSemester(Semester semester) {
        if (semester == null) {
            return;
        }
        if (!(this.student.getEdLevel().getValue() <= semester.getSemstrNum().getValue()) &&
                !this.semesters.contains(semester)) {
            this.semesters.add(semester);
        }
    }

    public double calculateAverage() {
        int totalSum = 0;
        int count = 0;
        int currentEdLevelValue = student.getEdLevel().getValue();

        for (Semester semester : semesters) {
            if (semester.getSemstrNum().getValue() >= currentEdLevelValue) {
                continue;
            }
            List<Subject> diffCredits = curriculum.getPlanForSemester(semester.getSemstrNum()).getDiffCredits();
            for (Subject s : diffCredits) {
                Mark mark = semester.getDiffCreditInfo(s);
                count++;
                totalSum += mark.getIntValue();
            }
            List<Subject> exams = curriculum.getPlanForSemester(semester.getSemstrNum()).getExams();
            for (Subject s : exams) {
                Mark mark = semester.getExamInfo(s);
                count++;
                totalSum += mark.getIntValue();
            }
        }
        return count > 0 ? (double) totalSum / count : 0.0;
    }

    public boolean canTransferToBudget() {
        if (this.student.getIsBudget()) {
            return true;
        }
        if (semesters.size() < 2 ||
                semesters.size() < (this.student.getEdLevel().getValue() - 1) ||
                this.student.getEdLevel() == EdLevel.GRADUATED) {
            return false;
        }
        Semester lastSem = null;
        Semester preLastSem = null;
        for (int i = 0; i < this.semesters.size(); i++) {
            if (this.semesters.get(i).getSemstrNum().getValue() == this.student.getEdLevel().getValue() - 1) {
                lastSem = this.semesters.get(i);
            }
            if (this.semesters.get(i).getSemstrNum().getValue() == this.student.getEdLevel().getValue() - 2) {
                preLastSem = this.semesters.get(i);
            }
        }
        return (successSession(lastSem) && successSession(preLastSem));
    }

    private boolean successSession(Semester semester) {
        if (semester == null) {
            return false;
        }
        for (Subject s : this.curriculum.getPlanForSemester(semester.getSemstrNum()).getExams()) {
            if (semester.getExamInfo(s).equals(Mark.SATISFACTORY)) {
                return false;
            }
        }
        return true;
    }

    public boolean canGetRedDiploma(Mark vkrMark) {
        if (semesters.isEmpty()) {
            return false;
        }
        if (semesters.size() < (this.student.getEdLevel().getValue() - 1)) {
            return false;
        }
        if (student.getEdLevel() == EdLevel.GRADUATED) {
            if (vkrMark != Mark.EXCELLENT) {
                return false;
            }
        }
        int excellentCount = 0;
        int totalGradedCount = 0;
        Map<Subject, Mark> finalGrades = getFinalGrades();
        for (Map.Entry<Subject, Mark> entry : finalGrades.entrySet()) {
            Mark mark = entry.getValue();
            if (mark == Mark.SATISFACTORY) {
                return false;
            }
            if (mark != Mark.CREDIT && mark != Mark.NOTEV) {
                totalGradedCount++;
                if (mark == Mark.EXCELLENT) {
                    excellentCount++;
                }
            }
        }
        if (totalGradedCount == 0) {
            return false;
        }
        return (double) excellentCount / totalGradedCount >= 0.75;
    }

    private Map<Subject, Mark> getFinalGrades() {
        EnumMap<Subject, Mark> finalGrades = new EnumMap<>(Subject.class);
        for (Semester sem : this.semesters) {
            for (Subject s : this.curriculum.getPlanForSemester(sem.getSemstrNum()).getExams()) {
                finalGrades.put(s, sem.getExamInfo(s));
            }
            for (Subject s : this.curriculum.getPlanForSemester(sem.getSemstrNum()).getDiffCredits()) {
                finalGrades.put(s, sem.getDiffCreditInfo(s));
            }
        }
        return finalGrades;
    }

    public boolean canGetIncreasedScholarship() { // в задании не указано, поэтому решил, что ПГАС - красный диплом
        if (!this.student.getIsBudget()) {
            return false;
        }
        if (!this.student.getEdLevel().equals(EdLevel.GRADUATED)) {
            return canGetRedDiploma(Mark.NOTEV);
        } else {
            return false;
        }
    }

    public Student getStudent() { return student; }
    public Curriculum getCurriculum() { return curriculum; }
    public List<Semester> getSemesters() { return semesters; }
}