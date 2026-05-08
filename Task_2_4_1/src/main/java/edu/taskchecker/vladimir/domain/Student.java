package edu.taskchecker.vladimir.domain;

public class Student {
    private final String name;
    private final String repoURL;
    private final String nickname;

    public Student(
            String name,
            String repoURL,
            String nickname
    ) {
        if (name == null || nickname == null || repoURL == null) {
            throw new IllegalArgumentException("must be non null");
        }

        if (name.isBlank() || repoURL.isBlank() || nickname.isBlank()) {
            throw new IllegalArgumentException("arguments must be non-empty");
        }

        this.name = name;
        this.repoURL = repoURL;
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public String getRepoURL() {
        return repoURL;
    }

    public String getNickname() {
        return nickname;
    }
}
