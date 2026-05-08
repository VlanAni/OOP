package edu.taskchecker.vladimir.domain;

import java.time.LocalDate;

public class TaskData {
    private final String id;
    private final String name;
    private final int maxScore;
    private final LocalDate softDeadline;
    private final LocalDate hardDeadline;

    public TaskData(
            String id,
            String name,
            int maxScore,
            LocalDate softDeadline,
            LocalDate hardDeadline
    ) {
        if (id == null || name == null || softDeadline == null || hardDeadline == null) {
            throw new IllegalArgumentException("must be non null");
        }

        if (maxScore < 1) {
            throw new IllegalArgumentException("maxScore must be positive");
        }

        if (id.isBlank() || name.isBlank()) {
            throw new IllegalArgumentException("id and name must be non-empty");
        }

        this.id = id;
        this.name = name;
        this.maxScore = maxScore;
        this.softDeadline = softDeadline;
        this.hardDeadline = hardDeadline;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public LocalDate getSoftDeadline() {
        return softDeadline;
    }

    public LocalDate getHardDeadline() {
        return hardDeadline;
    }
}
