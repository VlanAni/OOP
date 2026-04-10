package ru.vladimirdev.snake.managers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.vladimirdev.snake.commonservices.GameConfig;
import ru.vladimirdev.snake.domain.field.GameField;
import ru.vladimirdev.snake.domain.enums.DifficultyLevel;
import ru.vladimirdev.snake.domain.enums.Direction;
import ru.vladimirdev.snake.domain.enums.GameState;
import ru.vladimirdev.snake.domain.point.Point;
import ru.vladimirdev.snake.domain.field.GameCreator;
import ru.vladimirdev.snake.exceptions.CannotGetIntegerPropException;
import ru.vladimirdev.snake.exceptions.CannotLoadPropsException;

import java.util.*;

import static ru.vladimirdev.snake.managers.Palette.*;

public class Engine extends Application {

    private Stage stage;
    private Timeline loop;
    private Direction pendingDirection = null;
    private boolean dchangelock = false;
    private List<Point> prevBody  = Collections.emptyList();
    private Set<Point>  prevFoods = Collections.emptySet();

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

        int cell = calcCell(level.n, level.m);
        Canvas stateCanvas   = new Canvas(level.n * cell, level.m * cell);
        Canvas dynamicCanvas = new Canvas(level.n * cell, level.m * cell);
        StackPane root = new StackPane(stateCanvas, dynamicCanvas);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.sizeToScene();

        scene.setOnKeyPressed((KeyEvent e) -> {
            if (dchangelock) return;
            Direction d = null;
            switch (e.getCode()) {
                case UP:
                case W:
                    d = Direction.UP;
                    break;
                case DOWN:
                case S:
                    d = Direction.DOWN;
                    break;
                case LEFT:
                case A:
                    d = Direction.LEFT;
                    break;
                case RIGHT:
                case D:
                    d = Direction.RIGHT;
                    break;
                default:
                    break;
            }

            if (d != null) {
                pendingDirection = d;
                dchangelock  = true;
            }
        });

        GraphicsContext stateGC = stateCanvas.getGraphicsContext2D();
        stateGC.setFill(FIELDCOLOR.getColor());
        stateGC.fillRect(0, 0, stateCanvas.getWidth(), stateCanvas.getHeight());
        stateGC.setFill(WALLCOLOR.getColor());
        drawPoints(field.getWalls(), stateGC, cell);

        GraphicsContext dynamicGC = dynamicCanvas.getGraphicsContext2D();

        loop = new Timeline(new KeyFrame(Duration.millis(level.tickMs), e -> {
            if (pendingDirection != null) {
                field.changeDirection(pendingDirection);
                pendingDirection = null;
            }
            dchangelock = false;

            field.update();
            render(dynamicGC, field, cell);
            if (checkEndGame(field)) {
                endGame(field.getState());
            }
        }));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();
    }

    private void render(GraphicsContext gc, GameField field, int cell) {
        List<Point> body  = field.getPlayer().getBody();
        Set<Point>  foods = field.getFoods().keySet();

        if (prevBody.isEmpty()) {
            gc.setFill(SNAKECOLOR.getColor());
            for (Point p : body)
                gc.fillRect(p.getX() * cell, p.getY() * cell, cell, cell);
            gc.setFill(FOODCOLOR.getColor());
            for (Point p : foods)
                gc.fillRect(p.getX() * cell, p.getY() * cell, cell, cell);
        } else {
            if (body.size() == prevBody.size()) {
                Point oldTail = prevBody.get(prevBody.size() - 1);
                gc.clearRect(oldTail.getX() * cell, oldTail.getY() * cell, cell, cell);
            }

            for (Point p : prevFoods)
                if (!foods.contains(p))
                    gc.clearRect(p.getX() * cell, p.getY() * cell, cell, cell);

            gc.setFill(SNAKECOLOR.getColor());
            Point newHead = body.get(0);
            gc.fillRect(newHead.getX() * cell, newHead.getY() * cell, cell, cell);

            gc.setFill(FOODCOLOR.getColor());
            for (Point p : foods)
                if (!prevFoods.contains(p))
                    gc.fillRect(p.getX() * cell, p.getY() * cell, cell, cell);
        }

        prevBody  = new ArrayList<>(body);
        prevFoods = new HashSet<>(foods);
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
                            GraphicsContext gc,
                            int cell) {
        for (Point p : points) {
            gc.fillRect(p.getX() * cell, p.getY() * cell, cell, cell);
        }
    }

    private int calcCell(int cols, int rows) {
        int width, height, mincellsize;

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        int maxWidth  = (int) screen.getWidth();
        int maxHeight = (int) screen.getHeight();

        try {
            GameConfig gc = new GameConfig();
            width = gc.get("window.width");
            height = gc.get("window.height");
            mincellsize = gc.get("min.cell.size");
        } catch (CannotLoadPropsException | CannotGetIntegerPropException e) {
            width = maxWidth;
            height = maxHeight;
            mincellsize = 8;
        }

        int cellByWidth  = Math.min(width,  maxWidth)  / cols;
        int cellByHeight = Math.min(height, maxHeight) / rows;

        return Math.max(Math.min(cellByWidth, cellByHeight), mincellsize);
    }
}
