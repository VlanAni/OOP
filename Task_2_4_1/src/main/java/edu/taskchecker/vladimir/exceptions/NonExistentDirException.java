package edu.taskchecker.vladimir.exceptions;

import java.io.IOException;

public class NonExistentDirException extends IOException {
    public NonExistentDirException(String message) {
        super(message);
    }
}
