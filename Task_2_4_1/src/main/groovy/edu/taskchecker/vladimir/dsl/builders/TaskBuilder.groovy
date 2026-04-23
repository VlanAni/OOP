package edu.taskchecker.vladimir.dsl.builders

import edu.taskchecker.vladimir.domain.TaskData

import java.time.LocalDate

class TaskBuilder {
    String id
    String name
    int maxScore
    LocalDate softDeadline
    LocalDate hardDeadline

    void id(String v)           { id = v }
    void name(String v)         { name = v }
    void maxScore(int v)        { maxScore = v }
    void softDeadline(String v) { softDeadline = LocalDate.parse(v) }
    void hardDeadline(String v) { hardDeadline = LocalDate.parse(v) }

    TaskData build() {
        if (!id || !name || softDeadline == null || hardDeadline == null) {
            throw new IllegalStateException("empty fields")
        }

        if (maxScore < 1) {
            throw new IllegalStateException("maxScore must be positive")
        }

        return new TaskData(id, name, maxScore, softDeadline, hardDeadline)
    }
}

