package edu.taskchecker.vladimir.domain;

import java.time.LocalDate;

public class TaskResult {
    private final TaskData taskData;
    private final Student student;
    private final boolean successBuild;
    private final boolean matchStyle;
    private final TestStats testStat;
    private final LocalDate lastCommitDate;
    private final double score;

    public TaskResult (
            TaskData taskData,
            Student student,
            boolean successBuild,
            boolean matchStyle,
            TestStats testStat,
            LocalDate lastCommitDate,
            GradingConfig gconfig
    ) {
        if (taskData == null ||
                student == null ||
                lastCommitDate == null ||
                testStat == null ||
                gconfig == null) {
            throw new IllegalArgumentException("must be non null");
        }


        this.lastCommitDate = lastCommitDate;
        this.student = student;
        this.taskData = taskData;
        this.successBuild = successBuild;
        this.matchStyle = matchStyle;
        this.testStat = testStat;
        this.score = calcScore(gconfig);
    }

    public TaskData getTask() {
        return taskData;
    }

    public Student getStudent() {
        return student;
    }

    public boolean isSuccessBuild() {
        return successBuild;
    }

    public boolean isMatchStyle() {
        return matchStyle;
    }

    public TestStats getTestStat() {
        return testStat;
    }

    public LocalDate getLastCommitDate() {
        return lastCommitDate;
    }

    public double getScore() {
        return score;
    }

    private double calcScore(GradingConfig gconfig) {
        if (!this.isSuccessBuild()) {
            return 0;
        }

        if (this.getTestStat().getFailedTests() > 0) {
            return 0;
        }

        LocalDate today = this.getLastCommitDate();

        if (today.isAfter(this.getTask().getHardDeadline())) {
            return 0;
        } else if (today.isAfter(this.getTask().getSoftDeadline())) {
            return this.getTask().getMaxScore() *
                    gconfig.getSoftDeadlinePenalty() +
                    gconfig.getBonusFor(student.getName(), taskData.getId());
        } else {
            return this.getTask().getMaxScore() + gconfig.getBonusFor(student.getName(), taskData.getId());
        }
    }
}
