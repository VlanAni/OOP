package ru.vladimirdev.snake.domain.judge;

import ru.vladimirdev.snake.domain.field.GameField;

public interface Judge {
    boolean isVictory(GameField field);
}
