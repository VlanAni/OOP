package ru.vladimirdev.snake.domain.food;

import ru.vladimirdev.snake.domain.point.Point;
import ru.vladimirdev.snake.domain.snake.Snake;

public interface Food {
    Point getPosition();
    void affect(Snake snake);
}
