package edu.taskchecker.vladimir.domain;

import java.util.List;

public class CheckAssignment {
    private final List<Student> students;
    private final List<Task> tasks;

    public CheckAssignment(
            List<Student> students,
            List<Task> tasks
    ) {
        if (students == null || tasks == null) {
            throw new IllegalArgumentException("must be non null");
        }

        this.students = students;
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    public List<Student> getStudents() {
        return List.copyOf(students);
    }
}
