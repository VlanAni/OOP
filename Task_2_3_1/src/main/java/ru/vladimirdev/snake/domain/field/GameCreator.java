package ru.vladimirdev.snake.domain.field;

import ru.vladimirdev.snake.domain.enums.DifficultyLevel;
import ru.vladimirdev.snake.domain.food.Food;
import ru.vladimirdev.snake.domain.judge.Judge;
import ru.vladimirdev.snake.domain.judge.LengthCondition;
import ru.vladimirdev.snake.domain.point.Point;
import ru.vladimirdev.snake.domain.snake.Snake;

import java.util.Map;
import java.util.Set;

public class GameCreator {

    public static GameField createField(int n, int m, int foodAmount,
                                                int wallAmount, Judge judge) {
        return new GameField(n, m, foodAmount, wallAmount, judge);
    }

    public static GameField createTestField(int n, int m, Judge judge,
                                            Snake player, Set<Point> walls, Map<Point, Food> foods) {
        return new GameField(n, m, judge, player, walls, foods);
    }

    public static GameField createFromLevel(DifficultyLevel level) {
        Judge judge = new LengthCondition(level.length);

        return new GameField(level.n, level.m, level.foodAmount, level.wallAmount, judge);
    }
}