package ru.vladimirdev.snake.exceptions;

import java.io.IOException;

public class CannotLoadPropsException extends IOException {
    public CannotLoadPropsException(String message, Throwable cause) {
        super(message, cause);
    }
}
