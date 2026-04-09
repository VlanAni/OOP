package ru.vladimirdev.snake.exceptions;

import java.io.IOException;

public class CannotGetIntegerPropException extends IOException {
    public CannotGetIntegerPropException(String message, Throwable cause) {
        super(message, cause);
    }
}
