import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import vovchik.pizzasimulator.order.Order;
import vovchik.pizzasimulator.order.OrderStatus;
import vovchik.pizzasimulator.threadsafequeues.Storage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TestStorage {

    private Storage storage;

    @BeforeEach
    void setUp() {
        int CAPACITY = 2;
        storage = new Storage(CAPACITY);
    }

    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    void testPut() throws InterruptedException {
        storage.put(new Order(1, OrderStatus.STORED));
        storage.put(new Order(2, OrderStatus.STORED));
        Thread baker = new Thread(() -> {
            try {
                storage.put(new Order(3, OrderStatus.STORED));
            } catch (Exception ignored) {}
        });
        baker.start();
        Thread.sleep(200);

        assertEquals(Thread.State.WAITING, baker.getState());

        storage.get();
        baker.join(500);

        assertFalse(baker.isAlive());
    }



    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    void testDrain() throws InterruptedException {
        storage.put(new Order(1, OrderStatus.STORED));
        storage.put(new Order(2, OrderStatus.STORED));
        Thread baker = new Thread(() -> {
            try {
                storage.put(new Order(3, OrderStatus.STORED));
            } catch (Exception ignored) {}
        });
        baker.start();
        Thread.sleep(100);
        List<Order> removed = storage.drainOrders();

        assertEquals(2, removed.size());

        assert(storage.isEmpty());

        baker.join(500);
        assertFalse(baker.isAlive());
    }

    @Test
    void testGet() throws InterruptedException {
        Thread courier = new Thread(() -> {
            try {
                storage.get();
            } catch (Exception ignored) {}
        });
        courier.start();
        Thread.sleep(100);

        assertEquals(Thread.State.WAITING, courier.getState());

        storage.put(new Order(10, OrderStatus.STORED));
        courier.join(500);

        assertFalse(courier.isAlive());
    }
}
