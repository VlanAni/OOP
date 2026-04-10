package ru.vladimirdev.snake.domain.judge;

import ru.vladimirdev.snake.domain.field.GameField;

public class LengthCondition implements Judge {
    private final int targetLength;

    public LengthCondition(int targetLength) {
        this.targetLength = targetLength;
    }

    @Override
    public boolean isVictory(GameField field) {
        return field.getPlayerLength() >= targetLength;
    }
}
