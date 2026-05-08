package edu.taskchecker.vladimir.exceptions;

import java.io.IOException;

public class GitCommandFailedException extends IOException {
    public GitCommandFailedException(String message) {
        super(message);
    }
}
