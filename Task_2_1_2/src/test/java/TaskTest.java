import edu.vladimir.primesocket.domain.task.Task;
import edu.vladimir.primesocket.domain.task.TaskID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void mustCreateTaskAndReturnValues() {
        int[] chunk = {1, 2, 3, 4, 5};
        TaskID id = new TaskID(1);
        Task task = new Task(chunk, id);

        assertEquals(id, task.ID());
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, task.chunk());
    }

    @Test
    void mustReturnClonedArray() {
        int[] originalChunk = {10, 20, 30};
        Task task = new Task(originalChunk, new TaskID(2));

        int[] returnedChunk = task.chunk();
        returnedChunk[0] = 999;

        assertArrayEquals(new int[]{10, 20, 30}, task.chunk());
    }
}