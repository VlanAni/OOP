package vanisimov.creditbook.implementation;

import java.util.*;
import java.util.stream.Stream;

import lombok.Getter;
import vanisimov.creditbook.consts.EdLevel;
import vanisimov.creditbook.consts.Mark;
import vanisimov.creditbook.consts.Subject;

public class CreditBook {

    @Getter
    private final Student student; // студент
    @Getter
    private final Curriculum curriculum; // учебный план
    @Getter
    private final List<Semester> semesters; // список семестров

    public CreditBook(Student student, Curriculum curriculum) {
        this.student = student;
        this.curriculum = curriculum;
        this.semesters = new ArrayList<>();
    }

    public void addSemester(Semester semester) {
        if (semester == null) {
            return;
        }
        if (!(this.student.getEdLevel().getValue() <= semester.getSemesterNum().getValue()) &&
                !this.semesters.contains(semester)) {
            this.semesters.add(semester);
        }
    }

    public double calculateAverage() {
        List<Mark>grades = this.getAllGrades();
        if (grades == null || grades.isEmpty()) {
            return 0;
        } else {
            Stream<Mark> marks = grades.stream();
            try {
                OptionalDouble average = marks.mapToInt(Mark::getIntValue).average(); // получаем среднее значение
                if (average.isPresent()) {
                    return average.getAsDouble();
                } else {
                    return 0;
                }
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public boolean canTransferToBudget() {
        if (this.student.isBudget()) {
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
            if (this.semesters.get(i).getSemesterNum().getValue() == this.student.getEdLevel().getValue() - 1) {
                lastSem = this.semesters.get(i);
            }
            if (this.semesters.get(i).getSemesterNum().getValue() == this.student.getEdLevel().getValue() - 2) {
                preLastSem = this.semesters.get(i);
            }
        }
        return (successSession(lastSem) && successSession(preLastSem));
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
        List<Mark> finalGrades = getAllGrades();
        if (finalGrades == null) {
            return false;
        }
        if (finalGrades.stream().anyMatch(m -> m.equals(Mark.SATISFACTORY))) {
            return false;
        } else {
            long excellentCount = finalGrades.stream().
                    filter(m -> m.equals(Mark.EXCELLENT)).
                    count();
            long totalGradedCount = finalGrades.stream().
                    filter(m -> !(m.equals(Mark.CREDIT) || m.equals(Mark.NOTEV))).count();
            if (totalGradedCount == (long) 0) {
                return false;
            }
            return (double) excellentCount / totalGradedCount >= 0.75;
        }
    }

    public boolean canGetIncreasedScholarship() { // в задании не указано, поэтому решил, что ПГАС - красный диплом
        if (!this.student.isBudget()) {
            return false;
        }
        if (!this.student.getEdLevel().equals(EdLevel.GRADUATED)) {
            return canGetRedDiploma(Mark.NOTEV);
        } else {
            return false;
        }
    }

    // данные по семестрам в предмете
    private List<Mark> getAllGrades() {
        List<Mark> finalGrades = new ArrayList<>();
        for (Semester sem : this.semesters) {

            List<Subject> planPart = this.curriculum.getPlanForSemester(sem.getSemesterNum()).getCredits();
            List<Mark> valMarks = null;
            if (planPart.
                    stream().
                    map(sem::getCreditInfo).
                    anyMatch(Objects::isNull)) {
                return null;
            }

            // добавляем экзамены
            planPart = this.curriculum.getPlanForSemester(sem.getSemesterNum()).getExams();
            if (planPart.stream().map(sem::getExamInfo).anyMatch(Objects::isNull)) {
                return null;
            } else {
                valMarks = planPart.stream().map(sem::getExamInfo).toList();
                finalGrades.addAll(valMarks);
            }

            // добавляем диф.зачёты
            planPart = this.curriculum.getPlanForSemester(sem.getSemesterNum()).getDiffCredits();
            if (planPart.stream().map(sem::getDiffCreditInfo).anyMatch(Objects::isNull)) {
                return null;
            } else {
                valMarks = planPart.stream().map(sem::getDiffCreditInfo).toList();
                finalGrades.addAll(valMarks);
            }

        }
        return finalGrades;
    }

    private boolean successSession(Semester semester) {
        if (semester == null) {
            return false;
        }
        List<Subject> subjects = this.curriculum.getPlanForSemester(semester.getSemesterNum()).getExams();
        return subjects.
                stream().
                map(semester::getExamInfo).
                noneMatch(mark -> mark == null || mark.equals(Mark.SATISFACTORY));
    }
}