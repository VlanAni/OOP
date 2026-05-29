import edu.vladimir.primesocket.domain.task.TaskID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskIDTest {

    @Test
    void mustCreateTaskID() {
        TaskID id = new TaskID(5);
        assertEquals("5", id.toString());
    }

    @Test
    void mustThrowExceptionWhenIdIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new TaskID(-1));
    }

    @Test
    void mustBeEqualForSameValues() {
        TaskID id1 = new TaskID(10);
        TaskID id2 = new TaskID(10);
        TaskID id3 = new TaskID(20);

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());

        assertNotEquals(id1, id3);
    }
}