import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import vovchik.pizzasimulator.order.Order;
import vovchik.pizzasimulator.order.OrderStatus;
import vovchik.pizzasimulator.threadsafequeues.OrderQueue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TestOrderQueue {

    private OrderQueue queue;

    @BeforeEach
    void setUp() {
        queue = new OrderQueue();
    }

    @Test
    void testPutGetCorrectness() throws InterruptedException {
        Order o1 = new Order(1, OrderStatus.PENDING);
        Order o2 = new Order(2, OrderStatus.PENDING);
        queue.put(o1);
        queue.put(o2);

        assertEquals(o1, queue.get());

        assertEquals(o2, queue.get());
    }

    @Test
    void testDrain() {
        queue.put(new Order(1, OrderStatus.PENDING));
        queue.put(new Order(2, OrderStatus.PENDING));
        List<Order> drained = queue.drainOrders();

        assertEquals(2, drained.size());

        assertEquals(1, drained.get(0).getId());

        List<Order> secondDrain = queue.drainOrders();

        assertTrue(secondDrain.isEmpty());
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testBlockingGet() throws InterruptedException {
        Thread baker = new Thread(() -> {
            try {
                queue.get();
            } catch (Exception ignored) {}
        });
        baker.start();
        Thread.sleep(200);

        assertEquals(Thread.State.WAITING, baker.getState());

        queue.put(new Order(99, OrderStatus.PENDING));
        baker.join(500);

        assertFalse(baker.isAlive());
    }
}