package edu.taskchecker.vladimir.dsl.builders

import edu.taskchecker.vladimir.domain.Student

class StudentBuilder {
    String nickname
    String fullName
    String repoUrl

    void nickname(String v) { nickname = v }
    void fullName(String v) { fullName = v }
    void repoUrl(String v)  { repoUrl = v }

    Student build() {
        if (!nickname || !fullName || !repoUrl) {
            throw new IllegalStateException("empty fields")
        }

        return new Student(fullName, repoUrl, nickname)
    }
}