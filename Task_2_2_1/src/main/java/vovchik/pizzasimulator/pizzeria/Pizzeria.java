package vovchik.pizzasimulator.pizzeria;

import vovchik.pizzasimulator.jsonreader.JsonReader;
import vovchik.pizzasimulator.jsonreader.PizzeriaConfig;
import vovchik.pizzasimulator.order.Order;
import vovchik.pizzasimulator.threadsafequeues.OrderQueue;
import vovchik.pizzasimulator.threadsafequeues.Storage;
import vovchik.pizzasimulator.formatter.Formatter;
import vovchik.pizzasimulator.baker.Baker;
import vovchik.pizzasimulator.courier.Courier;

import java.util.ArrayList;
import java.util.List;

public class Pizzeria {

    private final OrderQueue orderQueue;
    private final Storage storage;
    private final List<Thread> bakers;
    private final List<Thread> couriers;
    private final JsonReader jsonReader;
    private final String ordersFilePath;

    public Pizzeria(String configPath, String ordersFilePath) {
        jsonReader = new JsonReader();
        this.ordersFilePath = ordersFilePath;
        PizzeriaConfig config = jsonReader.readConfig(configPath);
        orderQueue = new OrderQueue();
        storage = new Storage(config.storageCapacity);
        bakers = new ArrayList<>();
        couriers = new ArrayList<>();
        int bakerId = 1;
        for (var bakerConfig : config.bakers) {
            Baker baker = new Baker(bakerId++, bakerConfig.cookingTime, orderQueue, storage);
            bakers.add(new Thread(baker, "Baker-" + bakerId));
        }
        int courierId = 1;
        for (var courierConfig : config.couriers) {
            Courier courier = new Courier(courierId++, courierConfig.bagCapacity, courierConfig.deliveryTime, storage);
            couriers.add(new Thread(courier, "Courier-" + courierId));
        }
    }

    public void start() {
        Formatter.logSystem("Пиццерия открывается!...");
        for (Thread t : bakers) t.start();
        for (Thread t : couriers) t.start();
        List<Order> pendingOrders = jsonReader.readOrders(ordersFilePath);
        Formatter.logSystem("Загружено заказов на сегодня: " + pendingOrders.size());
        for (Order order : pendingOrders) {
            orderQueue.put(order);
        }
    }

    public void shutdown() {
        Formatter.logSystem("Пиццерия закрывается...");
        for (Thread t : bakers) t.interrupt();
        for (Thread t : couriers) t.interrupt();
        try {
            for (Thread t : bakers) t.join();
            for (Thread t : couriers) t.join();
        } catch (InterruptedException _) {
        }
        Formatter.logSystem("Все сотрудники остановили работу. Собираем незавершенные заказы...");
        List<Order> unfinished = new ArrayList<>();
        unfinished.addAll(orderQueue.drainOrders());
        unfinished.addAll(storage.drainOrders());
        jsonReader.writeOrders(ordersFilePath, unfinished);
        Formatter.logSystem("Пиццерия закрыта. Сохранено " + unfinished.size() + " заказов на завтра.");
    }
}
