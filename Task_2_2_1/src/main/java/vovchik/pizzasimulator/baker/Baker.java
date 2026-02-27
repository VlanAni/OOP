package vovchik.pizzasimulator.baker;

import vovchik.pizzasimulator.order.Order;
import vovchik.pizzasimulator.order.OrderStatus;
import vovchik.pizzasimulator.formatter.Formatter;
import vovchik.pizzasimulator.threadsafequeues.OrderQueue;
import vovchik.pizzasimulator.threadsafequeues.Storage;

public class Baker implements Runnable {
    private final int id;
    private final int cookingTime;
    private final OrderQueue orderQueue;
    private final Storage storage;

    public Baker(int id, int cookingTime, OrderQueue orderQueue, Storage storage) {
        this.id = id;
        this.cookingTime = cookingTime;
        this.orderQueue = orderQueue;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = orderQueue.get();
                order.setStatus(OrderStatus.BAKING);
                Formatter.logOrder(order.getId(), order.getStatus());
                Thread.sleep(cookingTime * 1000L);
                storage.put(order);
                order.setStatus(OrderStatus.STORED);
                Formatter.logOrder(order.getId(), order.getStatus());
            }
            Formatter.logSystem("Пекарь " + id + " закончил рабочий день");
        } catch (InterruptedException e) {
            Formatter.logSystem("Пекарь " + id + " закончил рабочий день");
        }
    }
}
