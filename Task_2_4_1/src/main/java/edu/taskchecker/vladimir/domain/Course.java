package edu.taskchecker.vladimir.domain;

import java.util.List;

public class Course {
    private final List<Task> tasks;
    private final List<Group> groups;
    private final List<ControlPoint> controlPoints;
    private final List<CheckAssignment> assignments;
    private final GradingConfig gradingConfig;

    public Course(
            List<Task> tasks,
            List<Group> groups,
            List<ControlPoint> checkpoints,
            List<CheckAssignment> assignments,
            GradingConfig gradingConfig
    ) {
        if (tasks == null ||
                groups == null ||
                checkpoints == null ||
                assignments == null ||
                gradingConfig == null) {
            throw new IllegalArgumentException("must be non null");
        }

        this.tasks = tasks;
        this.groups = groups;
        this.controlPoints = checkpoints;
        this.assignments = assignments;
        this.gradingConfig = gradingConfig;
    }

    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    public List<Group> getGroups() {
        return List.copyOf(groups);
    }

    public List<ControlPoint> getControlPoints() {
        return List.copyOf(controlPoints);
    }

    public List<CheckAssignment> getAssignments() {
        return List.copyOf(assignments);
    }

    public GradingConfig getGradingConfig() {
        return gradingConfig;
    }
}
