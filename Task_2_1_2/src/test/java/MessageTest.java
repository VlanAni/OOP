import edu.vladimir.primesocket.domain.message.Message;
import edu.vladimir.primesocket.domain.message.MessageType;
import edu.vladimir.primesocket.domain.task.Task;
import edu.vladimir.primesocket.domain.task.TaskID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void mustCreateShutdownMessage() {
        Message message = new Message();

        assertEquals(MessageType.SHUTDOWN, message.type());
        assertNull(message.task());
        assertNull(message.result());
    }

    @Test
    void mustCreateNewTaskMessage() {
        Task task = new Task(new int[]{1, 2}, new TaskID(1));
        Message message = new Message(task);

        assertEquals(MessageType.NEWTASK, message.type());
        assertEquals(task, message.task());
        assertNull(message.result());
    }

    @Test
    void mustCreateTaskResultMessage() {
        Message message = new Message(true);

        assertEquals(MessageType.TASKRESULT, message.type());
        assertTrue(message.result());
        assertNull(message.task());
    }
}