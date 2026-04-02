import org.junit.jupiter.api.Test;
import ru.vladimirdev.snake.domain.field.GameField;
import ru.vladimirdev.snake.domain.enums.Direction;
import ru.vladimirdev.snake.domain.enums.GameState;
import ru.vladimirdev.snake.domain.food.Apple;
import ru.vladimirdev.snake.domain.food.Food;
import ru.vladimirdev.snake.domain.point.Point;
import ru.vladimirdev.snake.domain.snake.Snake;
import ru.vladimirdev.snake.domain.field.GameCreator;
import ru.vladimirdev.snake.domain.judge.LengthCondition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {

    @Test
    void testVictoryCondition() {
        Snake snake = new Snake(5, 5);
        Map<Point, Food> foods = new HashMap<>();
        foods.put(new Point(5, 4), new Apple());

        LengthCondition winCondition = new LengthCondition(2);
        GameField field = GameCreator.createTestField(10, 10, winCondition, snake, null, foods);

        field.update();
        field.update();

        assertEquals(GameState.VICTORY, field.getState());
    }

    @Test
    void testWallCollision() {
        Snake snake = new Snake(5, 5);
        Set<Point> walls = new HashSet<>();

        walls.add(new Point(5, 4));

        GameField field = GameCreator.createTestField(10, 10, null, snake, walls, null);

        field.update();

        assertEquals(GameState.GAME_OVER, field.getState());
    }

    @Test
    void testSelfCollision() {
        Snake snake = new Snake(5, 5);

        GameField field = GameCreator.createTestField(10, 10, null, snake, null, null);

        for (int i = 0; i < 4; i++) {
            snake.eat();
            field.update();
        }

        field.changeDirection(Direction.LEFT);
        field.update();

        field.changeDirection(Direction.DOWN);
        field.update();

        field.changeDirection(Direction.RIGHT);
        field.update();

        assertEquals(GameState.GAME_OVER, field.getState());
    }

    @Test
    void testTopBorderCollision() {
        Snake snake = new Snake(0, 0);

        GameField field = GameCreator.createTestField(5, 5, null, snake, null, null);

        field.update();

        assertEquals(GameState.GAME_OVER, field.getState());
    }

    @Test
    void testLeftBorderCollision() {
        Snake snake = new Snake(0, 0);

        GameField field = GameCreator.createTestField(5, 5, null, snake, null, null);

        field.changeDirection(Direction.LEFT);
        field.update();

        assertEquals(GameState.GAME_OVER, field.getState());
    }

    @Test
    void testRightBorderCollision() {
        Snake snake = new Snake(4, 0);

        GameField field = GameCreator.createTestField(5, 5, null, snake, null, null);

        field.changeDirection(Direction.RIGHT);
        field.update();

        assertEquals(GameState.GAME_OVER, field.getState());
    }

    @Test
    void testBottomBorderCollision() {
        Snake snake = new Snake(0, 4);

        GameField field = GameCreator.createTestField(5, 5, null, snake, null, null);

        field.changeDirection(Direction.RIGHT);
        field.changeDirection(Direction.DOWN);
        field.update();

        assertEquals(GameState.GAME_OVER, field.getState());
    }
}