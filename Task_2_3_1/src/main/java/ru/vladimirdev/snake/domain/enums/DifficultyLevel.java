package ru.vladimirdev.snake.domain.enums;

public enum DifficultyLevel {
    EASY   (15, 15, 2,  0,  10, 400),
    MEDIUM (20, 20, 2,  15, 15, 250),
    HARD   (30, 30, 3,  40, 20, 130);

    public final int n;
    public final int m;
    public final int foodAmount;
    public final int wallAmount;
    public final int length;
    public final int tickMs;

    DifficultyLevel(int n, int m, int food, int walls, int length, int tick) {
        this.n = n; this.m = m;
        this.foodAmount = food; this.wallAmount = walls;
        this.length = length; this.tickMs = tick;
    }
}
