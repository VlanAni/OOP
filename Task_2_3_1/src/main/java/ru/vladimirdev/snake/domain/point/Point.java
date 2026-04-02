package ru.vladimirdev.snake.domain.point;

import static java.util.Objects.hash;

public class Point {
    private final int X;
    private final int Y;

    public Point(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Point point = (Point) obj;
        return point.X == this.X && point.Y == this.Y;
    }

    @Override
    public int hashCode() {
        return hash(this.X, this.Y);
    }
}
