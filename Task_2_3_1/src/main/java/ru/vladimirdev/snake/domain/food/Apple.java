package ru.vladimirdev.snake.domain.food;

import ru.vladimirdev.snake.domain.point.Point;
import ru.vladimirdev.snake.domain.snake.Snake;

public class Apple implements Food{
    private final Point position;

    public Apple(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void affect(Snake snake) {
        snake.eat();
    }
}
