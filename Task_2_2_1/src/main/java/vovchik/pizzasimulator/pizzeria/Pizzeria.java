package vovchik.pizzasimulator.pizzeria;

import vovchik.pizzasimulator.jsonreader.JsonReader;
import vovchik.pizzasimulator.jsonreader.PizzeriaConfig;
import vovchik.pizzasimulator.order.Order;
import vovchik.pizzasimulator.threadsafequeues.OrderQueue;
import vovchik.pizzasimulator.threadsafequeues.Storage;
import vovchik.pizzasimulator.formatter.Formatter;
import vovchik.pizzasimulator.baker.Baker;
import vovchik.pizzasimulator.courier.Courier;
import vovchik.pizzasimulator.threadsafequeues.ThreadSafeQueue;

import java.util.ArrayList;
import java.util.List;

public class Pizzeria {

    private final ThreadSafeQueue orderQueue;
    private final ThreadSafeQueue storage;

    private final List<Baker> bakerWorkers;
    private final List<Courier> courierWorkers;

    private final List<Thread> bakerThreads;
    private final List<Thread> courierThreads;

    private final JsonReader jsonReader;
    private final String ordersFilePath;

    public Pizzeria(String configPath, String ordersFilePath) {
        jsonReader = new JsonReader();
        this.ordersFilePath = ordersFilePath;
        PizzeriaConfig config = jsonReader.readConfig(configPath);

        orderQueue = new OrderQueue();
        storage = new Storage(config.storageCapacity);

        bakerWorkers = new ArrayList<>();
        courierWorkers = new ArrayList<>();
        bakerThreads = new ArrayList<>();
        courierThreads = new ArrayList<>();

        int bakerId = 1;
        for (var bakerConfig : config.bakers) {
            Baker baker = new Baker(bakerId++, bakerConfig.cookingTime, orderQueue, storage);
            bakerWorkers.add(baker);
            bakerThreads.add(new Thread(baker, "Baker-" + bakerId));
        }

        int courierId = 1;
        for (var courierConfig : config.couriers) {
            Courier courier = new Courier(courierId++, courierConfig.bagCapacity, courierConfig.deliveryTime, storage);
            courierWorkers.add(courier);
            courierThreads.add(new Thread(courier, "Courier-" + courierId));
        }
    }

    public void start() {
        Formatter.logSystem("Пиццерия открывается!...");
        for (Thread t : bakerThreads) t.start();
        for (Thread t : courierThreads) t.start();

        List<Order> pendingOrders = jsonReader.readOrders(ordersFilePath);
        Formatter.logSystem("Загружено заказов на сегодня: " + pendingOrders.size());
        try {
            for (Order order : pendingOrders) {
                orderQueue.put(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        Formatter.logSystem("Пиццерия закрывается...");

        for (Thread t : bakerThreads) t.interrupt();
        for (Thread t : courierThreads) t.interrupt();

        List<Order> unfinished = new ArrayList<>();
        unfinished.addAll(orderQueue.drainOrders());
        unfinished.addAll(storage.drainOrders());

        try {
            for (Thread t : bakerThreads) t.join();
            for (Thread t : courierThreads) t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (Baker baker : bakerWorkers) {
            Order order = baker.getOrderInWork();
            if (order != null) {
                unfinished.add(order);
            }
        }

        for (Courier courier : courierWorkers) {
            List<Order> orders = courier.getOrdersInBag();
            if (orders != null && !orders.isEmpty()) {
                unfinished.addAll(orders);
            }
        }

        jsonReader.writeOrders(ordersFilePath, unfinished);
        Formatter.logSystem("Пиццерия закрыта. Сохранено " + unfinished.size() + " заказов на завтра.");
    }
}
