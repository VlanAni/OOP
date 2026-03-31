package ru.vladimirdev.snake.domain.snake;

import ru.vladimirdev.snake.domain.enums.Direction;
import ru.vladimirdev.snake.domain.point.Point;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    private final List<Point> links;
    private Direction direct;
    private boolean foodFlag;
    private int length;
    private int velocity;

    public Snake(int X, int Y) {
        if (X < 0 || Y < 0) {
            throw new IllegalArgumentException("start point mustn't have negative coordinates");
        }

        length = 1;
        velocity = 1;
        links = new LinkedList<>();
        links.add(new Point(X, Y));
        direct = Direction.UP;
        foodFlag = false;
    }

    public void switchDirect(Direction d) {
        if (this.direct == Direction.UP && d == Direction.DOWN) return;
        if (this.direct == Direction.DOWN && d == Direction.UP) return;
        if (this.direct == Direction.LEFT && d == Direction.RIGHT) return;
        if (this.direct == Direction.RIGHT && d == Direction.LEFT) return;

        this.direct = d;
    }

    public void move() {
        Point head = links.get(0);
        int newX = head.shareX();
        int newY = head.shareY();

        switch (direct) {
            case UP -> newY -= velocity;
            case DOWN -> newY += velocity;
            case LEFT -> newX -= velocity;
            case RIGHT -> newX += velocity;
        }

        links.add(0, new Point(newX, newY));

        if (!foodFlag) {
            links.remove(links.size() - 1);
        } else {
            foodFlag = false;
            length++;
        }
    }

    public void eat() {
        foodFlag = true;
    }

    public Point myHead() {
        return links.get(0);
    }

    public int myLength() {
        return length;
    }

    public List<Point> myTail() {
        return links;
    }

    public void increaseVelocity() {
        this.velocity++;
    }
}
