package ru.vladimirdev.snake.services;

import ru.vladimirdev.snake.domain.GameField;

public interface Judge {
    boolean isVictory(GameField field);
}
