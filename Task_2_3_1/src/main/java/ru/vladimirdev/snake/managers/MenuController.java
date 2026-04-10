package ru.vladimirdev.snake.managers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import ru.vladimirdev.snake.domain.enums.DifficultyLevel;

import java.util.function.Consumer;

public class MenuController {

    private Consumer<DifficultyLevel> onLevelSelected;

    public void setOnLevelSelected(Consumer<DifficultyLevel> callback) {
        this.onLevelSelected = callback;
    }

    @FXML
    private void onEasy() {
        onLevelSelected.accept(DifficultyLevel.EASY);
    }

    @FXML
    private void onMedium() {
        onLevelSelected.accept(DifficultyLevel.MEDIUM);
    }

    @FXML
    private void onHard() {
        onLevelSelected.accept(DifficultyLevel.HARD);
    }

    @FXML
    private void onQuit() {
        Platform.exit();
    }
}
