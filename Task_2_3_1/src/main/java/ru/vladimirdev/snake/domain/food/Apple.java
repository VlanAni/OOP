package ru.vladimirdev.snake.domain.food;

import ru.vladimirdev.snake.domain.snake.Snake;

public class Apple implements Food{

    public Apple() {
    }

    @Override
    public void affect(Snake snake) {
        snake.eat();
    }
}
