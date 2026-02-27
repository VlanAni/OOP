import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vovchik.pizzasimulator.jsonreader.JsonReader;
import vovchik.pizzasimulator.jsonreader.PizzeriaConfig;
import vovchik.pizzasimulator.order.Order;
import vovchik.pizzasimulator.order.OrderStatus;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestJsonReader {

    private JsonReader jsonReader;

    @BeforeEach
    void setUp() {
        jsonReader = new JsonReader();
    }

    private String getResourcePath(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("Файл не найден в ресурсах: " + fileName);
        }
        return resource.getPath();
    }

    @Test
    void testReadOrders() {
        String path = getResourcePath("test_orders.json");
        List<Order> orders = jsonReader.readOrders(path);

        assertNotNull(orders);

        assertFalse(orders.isEmpty());

        Order first = orders.get(0);

        assertTrue(first.getId() > 0);

        assertEquals(OrderStatus.PENDING, first.getStatus());
    }

    @Test
    void testReadConfig() {
        PizzeriaConfig config = jsonReader.readConfig("test_settings.json");

        assertNotNull(config);

        assertTrue(config.storageCapacity > 0);

        assertFalse(config.bakers.isEmpty());
    }
}