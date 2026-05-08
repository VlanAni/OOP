package edu.taskchecker.vladimir.domain;

import java.time.LocalDate;

public class ControlPoint {
    private final String name;
    private final LocalDate date;

    public ControlPoint(
            String name,
            LocalDate date
    ) {
        if (name == null || date == null) {
            throw new IllegalArgumentException("must be non null");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("name must be non-empty");
        }

        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }
}
