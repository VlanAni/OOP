package ru.vladimirdev.snake.services;

import ru.vladimirdev.snake.domain.GameField;

public class LengthCondition implements Judge {
    private final int targetLength;

    public LengthCondition(int targetLength) {
        this.targetLength = targetLength;
    }

    @Override
    public boolean isVictory(GameField field) {
        return field.getPlayer().myLength() >= targetLength;
    }
}
