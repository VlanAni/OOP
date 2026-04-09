package ru.vladimirdev.snake.domain.enums;

import ru.vladimirdev.snake.commonservices.GameConfig;
import ru.vladimirdev.snake.exceptions.CannotGetIntegerPropException;
import ru.vladimirdev.snake.exceptions.CannotLoadPropsException;

public enum DifficultyLevel {
    EASY("easy"), MEDIUM("medium"), HARD("hard");

    public int n, m, foodAmount, wallAmount, length, tickMs;

    DifficultyLevel(String prefix) {
        try {
            GameConfig gc = new GameConfig();
            setParams(gc.get(prefix + ".n"), gc.get(prefix + ".m"),
                    gc.get(prefix + ".foodAmount"), gc.get(prefix + ".wallAmount"),
                    gc.get(prefix + ".targetLength"), gc.get(prefix + ".tickMs"));
        } catch (CannotLoadPropsException | CannotGetIntegerPropException e)  {
            switch (prefix) {
                case "easy":
                    setParams(15, 15, 2,  0,  10, 400);
                    break;
                case "medium":
                    setParams(20, 20, 2,  15, 15, 250);
                    break;
                case "hard":
                    setParams(100, 200, 3,  40, 20, 130);
                    break;
            }
        }
    }

    private void setParams(int n, int m, int foodAmount,
                           int wallAmount, int length, int tickMs) {
        this.n = n;
        this.m = m;
        this.foodAmount = foodAmount;
        this.wallAmount = wallAmount;
        this.length = length;
        this.tickMs = tickMs;
    }
}
