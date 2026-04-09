import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vladimirdev.snake.domain.enums.Direction;
import ru.vladimirdev.snake.domain.point.Point;
import ru.vladimirdev.snake.domain.snake.Snake;

import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {
    private Snake snake;

    @BeforeEach
    void setUp() {
        snake = new Snake(5, 5);
    }

    @Test
    void testInitialState() {
        assertEquals(1, snake.getLength());
        assertEquals(new Point(5, 5), snake.getHead());
    }

    @Test
    void testMoveUp() {
        snake.move();
        assertEquals(new Point(5, 4), snake.getHead());
        assertEquals(1, snake.getLength());
    }

    @Test
    void testEatAndGrow() {
        snake.eat();
        snake.move();

        assertEquals(2, snake.getLength());
        assertEquals(new Point(5, 4), snake.getBody().get(0));
        assertEquals(new Point(5, 5), snake.getBody().get(1));
    }

    @Test
    void testCannotReverseDirection() {
        snake.switchDirect(Direction.DOWN);
        snake.move();

        assertEquals(new Point(5, 4), snake.getHead());
    }

    @Test
    void testCannotReverseDOWNtoUP() {
        snake.switchDirect(Direction.RIGHT);
        snake.move();
        snake.switchDirect(Direction.DOWN);
        snake.move();
        snake.switchDirect(Direction.UP);
        snake.move();
        assertEquals(new Point(6, 7), snake.getHead());
    }

    @Test
    void testCannotReverseLEFTtoRIGHT() {
        snake.switchDirect(Direction.LEFT);
        snake.move();
        snake.switchDirect(Direction.RIGHT);
        snake.move();
        assertEquals(new Point(3, 5), snake.getHead());
    }

    @Test
    void testCannotReverseRIGHTtoLEFT() {
        snake.switchDirect(Direction.RIGHT);
        snake.move();
        snake.switchDirect(Direction.LEFT);
        snake.move();
        assertEquals(new Point(7, 5), snake.getHead());
    }
}