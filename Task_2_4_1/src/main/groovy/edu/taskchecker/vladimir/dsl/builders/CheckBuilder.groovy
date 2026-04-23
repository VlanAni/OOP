package edu.taskchecker.vladimir.dsl.builders

class CheckBuilder {
    List<String> studentNicknames = []
    List<String> taskIds = []

    void students(String... nicknames) {
        studentNicknames.addAll(nicknames)
    }

    void tasks(String... ids) {
        taskIds.addAll(ids)
    }
}