package vovchik.pizzasimulator.jsonreader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import vovchik.pizzasimulator.order.Order;
import vovchik.pizzasimulator.order.OrderStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    private final ObjectMapper mapper;

    public JsonReader() {
        mapper = new ObjectMapper();
    }

    public PizzeriaConfig readConfig(String path) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Файл не найден в ресурсах: " + path);
            }
            return mapper.readValue(inputStream, PizzeriaConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения конфига из ресурсов: " + e.getMessage());
        }
    }

    public List<Order> readOrders(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println("Ошибка при чтении заказов: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void writeOrders(String path, List<Order> unfinishedOrders) {
        try {
            for (Order o : unfinishedOrders) {
                o.setStatus(OrderStatus.PENDING);
            }
            mapper.writeValue(new File(path), unfinishedOrders);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении заказов: " + e.getMessage());
        }
    }
}