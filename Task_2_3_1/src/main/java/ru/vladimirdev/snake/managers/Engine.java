package ru.vladimirdev.snake.managers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.vladimirdev.snake.domain.field.GameField;
import ru.vladimirdev.snake.domain.enums.DifficultyLevel;
import ru.vladimirdev.snake.domain.enums.Direction;
import ru.vladimirdev.snake.domain.enums.GameState;
import ru.vladimirdev.snake.domain.point.Point;
import ru.vladimirdev.snake.domain.field.GameCreator;

import java.util.*;

import static ru.vladimirdev.snake.managers.Palette.*;

public class Engine extends Application {

    private static final int CELL = 20;

    private Stage stage;

    private Timeline loop;

    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("Snake");
        this.stage.setResizable(false);
        showMenu();
        this.stage.show();
    }

    private void showMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/SnakeGameInterface.fxml")
        );
        Parent root = loader.load();

        MenuController ctrl = loader.getController();
        ctrl.setOnLevelSelected(this::startGame);

        stage.setScene(new Scene(root));
        stage.sizeToScene();
    }

    private void startGame(DifficultyLevel level) {
        GameField field = GameCreator.createFromLevel(level);

        Canvas stateCanvas = new Canvas(level.n * CELL, level.m * CELL);
        Canvas dynamicCanvas = new Canvas(level.n * CELL, level.m * CELL);
        StackPane root = new StackPane(stateCanvas, dynamicCanvas);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.sizeToScene();

        scene.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()) {
                case UP:
                case W:
                    field.changeDirection(Direction.UP);
                    break;
                case DOWN:
                case S:
                    field.changeDirection(Direction.DOWN);
                    break;
                case LEFT:
                case A:
                    field.changeDirection(Direction.LEFT);
                    break;
                case RIGHT:
                case D:
                    field.changeDirection(Direction.RIGHT);
                    break;
                default:
                    break;
            }
        });

        GraphicsContext stateGC = stateCanvas.getGraphicsContext2D();
        stateGC.setFill(FIELDCOLOR.getColor());
        stateGC.fillRect(0, 0, stateCanvas.getWidth(), stateCanvas.getHeight());
        stateGC.setFill(WALLCOLOR.getColor());
        drawPoints(field.getWalls(), stateGC);

        GraphicsContext dynamicGC = dynamicCanvas.getGraphicsContext2D();

        loop = new Timeline(
                       new KeyFrame(Duration.millis(level.tickMs), e -> {
                field.update();
                render(dynamicGC, field, dynamicCanvas);
                if (checkEndGame(field)) {
                    endGame(field.getState());
                }
            }));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();
    }

    private void render(GraphicsContext gc,
                        GameField field,
                        Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(FOODCOLOR.getColor());
        drawPoints(field.getFoods().keySet(), gc);
        gc.setFill(SNAKECOLOR.getColor());
        drawPoints(field.getPlayer().getBody(), gc);
    }

    private boolean checkEndGame(GameField field) {
        GameState state = field.getState();
        return (state == GameState.GAME_OVER ||
                state == GameState.VICTORY);
    }

    private void endGame(GameState gameState) {
        loop.stop();
        String msg = gameState == GameState.VICTORY ? "Victory!" : "You lose";
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
            alert.setTitle("Your result");
            alert.setHeaderText(null);
            alert.showAndWait();
            Platform.exit();
        });
    }

    private void drawPoints(Collection<Point> points,
                            GraphicsContext gc) {
        for (Point p : points) {
            gc.fillRect(p.getX() * CELL, p.getY() * CELL, CELL, CELL);
        }
    }
}
