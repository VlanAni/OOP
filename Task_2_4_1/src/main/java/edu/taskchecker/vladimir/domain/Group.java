package edu.taskchecker.vladimir.domain;

import java.util.List;

public class Group {
    private final String name;
    private final List<Student> students;

    public Group (
            String name,
            List<Student> students
    ) {
        if (name == null || students == null) {
            throw new IllegalArgumentException("must be non null");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("group must have non-empty name");
        }

        this.name = name;
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return List.copyOf(students);
    }
}
