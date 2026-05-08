package edu.taskchecker.vladimir.domain;

import java.util.List;

public class CheckAssignment {
    private final List<Student> students;
    private final List<TaskData> taskData;

    public CheckAssignment(
            List<Student> students,
            List<TaskData> taskData
    ) {
        if (students == null || taskData == null) {
            throw new IllegalArgumentException("must be non null");
        }

        this.students = students;
        this.taskData = taskData;
    }

    public List<TaskData> getTasks() {
        return List.copyOf(taskData);
    }

    public List<Student> getStudents() {
        return List.copyOf(students);
    }
}
