import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vladimirdev.snake.domain.GameField;
import ru.vladimirdev.snake.domain.enums.Direction;
import ru.vladimirdev.snake.domain.enums.GameState;
import ru.vladimirdev.snake.services.Judge;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {

    private GameField field;
    private Judge neverWin;

    @BeforeEach
    void setUp() {
        neverWin = f -> false;

        field = new GameField(10, 10, 1, 0, neverWin);
    }

    @Test
    void testStartState() {
        assertEquals(GameState.PLAYING, field.shareState());

        assertNotNull(field.getPlayer());

        assertEquals(1, field.getPlayer().myLength());

        assertEquals(5, field.getPlayer().myHead().shareX());

        assertEquals(5, field.getPlayer().myHead().shareY());
    }

    @Test
    void testTopWallColl() {
        for (int i = 0; i < 5; i++) {
            field.update();
            assertEquals(GameState.PLAYING, field.shareState());
        }
        field.update();

        assertEquals(GameState.GAME_OVER, field.shareState());
    }

    @Test
    void testLeftWallColl() {
        field.getPlayer().switchDirect(Direction.LEFT);
        for (int i = 0; i < 5; i++) {
            field.update();
        }
        field.update();

        assertEquals(GameState.GAME_OVER, field.shareState());
    }

    @Test
    void testVictoryCondition() {
        Judge winJudge = f -> true;
        GameField winField = new GameField(10, 10, 1, 0, winJudge);
        winField.update();

        assertEquals(GameState.VICTORY, winField.shareState());
    }
}