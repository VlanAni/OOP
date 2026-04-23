package edu.taskchecker.vladimir.exceptions;

import java.io.IOException;

public class BuildCommandFailedException extends IOException {
    public BuildCommandFailedException(String message) {
        super(message);
    }
}
