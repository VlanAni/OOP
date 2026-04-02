package ru.vladimirdev.snake.managers;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

enum Palette {

    FIELDCOLOR(Color.web("#1a1a1a")),
    FOODCOLOR(Color.RED),
    SNAKECOLOR(Color.LIME),
    WALLCOLOR(Color.YELLOW);

    private final Paint color;

    Palette(Paint color) {
        this.color = color;
    }

    Paint getColor() {
        return color;
    }
}
