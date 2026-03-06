package vovchik.pizzasimulator.baker;

import vovchik.pizzasimulator.order.Order;
import vovchik.pizzasimulator.order.OrderStatus;
import vovchik.pizzasimulator.formatter.Formatter;
import vovchik.pizzasimulator.threadsafequeues.ThreadSafeQueue;

public class Baker implements Runnable {
    private final int id;
    private final int cookingTime;
    private final ThreadSafeQueue orderQueue;
    private final ThreadSafeQueue storage;

    private Order currentOrder;

    public Baker(int id, int cookingTime, ThreadSafeQueue orderQueue, ThreadSafeQueue storage) {
        this.id = id;
        this.cookingTime = cookingTime;
        this.orderQueue = orderQueue;
        this.storage = storage;
        this.currentOrder = null;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                currentOrder = orderQueue.get();
                currentOrder.setStatus(OrderStatus.BAKING);
                Formatter.logOrder(currentOrder.getId(), currentOrder.getStatus());

                Thread.sleep(cookingTime * 1000L);

                storage.put(currentOrder);
                currentOrder.setStatus(OrderStatus.STORED);
                Formatter.logOrder(currentOrder.getId(), currentOrder.getStatus());

                currentOrder = null;
            }
            Formatter.logSystem("Пекарь " + id + " закончил рабочий день");
        } catch (InterruptedException e) {
            Formatter.logSystem("Пекарь " + id + " закончил рабочий день");
        }
    }

    public Order getOrderInWork() {
        return currentOrder;
    }
}