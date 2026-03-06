import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import vovchik.pizzasimulator.jsonreader.JsonReader;
import vovchik.pizzasimulator.order.Order;
import vovchik.pizzasimulator.order.OrderStatus;
import vovchik.pizzasimulator.pizzeria.Pizzeria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestPizzeria {

    @TempDir
    Path tempDir;

    @Test
    void testPizzeriaFullCycle() throws IOException, InterruptedException {
        String configPath = "test_settings.json";
        Path ordersPath = tempDir.resolve("test_orders.json");

        String ordersJson = """
            [
              {"id": 1, "status": "PENDING"},
              {"id": 2, "status": "PENDING"},
              {"id": 3, "status": "PENDING"},
              {"id": 4, "status": "PENDING"},
              {"id": 5, "status": "PENDING"}
            ]
            """;
        Files.writeString(ordersPath, ordersJson);

        Pizzeria pizzeria = new Pizzeria(configPath, ordersPath.toString());
        pizzeria.start();

        Thread.sleep(5000);

        pizzeria.shutdown();

        JsonReader reader = new JsonReader();
        List<Order> remainingOrders = reader.readOrders(ordersPath.toString());

        assertNotNull(remainingOrders);

        assertTrue(remainingOrders.size() < 5);

        if (!remainingOrders.isEmpty()) {
            assertEquals(OrderStatus.PENDING, remainingOrders.get(0).getStatus());
        }
    }
}