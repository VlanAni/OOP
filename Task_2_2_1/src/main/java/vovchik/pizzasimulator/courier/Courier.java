package vovchik.pizzasimulator.courier;

import vovchik.pizzasimulator.order.Order;
import vovchik.pizzasimulator.order.OrderStatus;
import vovchik.pizzasimulator.formatter.Formatter;
import vovchik.pizzasimulator.threadsafequeues.ThreadSafeQueue;

import java.util.ArrayList;
import java.util.List;

public class Courier implements Runnable {
    private final int id;
    private final int bagCapacity;
    private final int deliveryTime;
    private final ThreadSafeQueue storage;
    private final List<Order> bag;

    public Courier(int id, int trunkCapacity, int deliveryTime, ThreadSafeQueue storage) {
        this.id = id;
        this.bagCapacity = trunkCapacity;
        this.deliveryTime = deliveryTime;
        this.storage = storage;
        this.bag = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order firstOrder = storage.get();
                bag.add(firstOrder);

                while (bag.size() < bagCapacity && !storage.isEmpty()) {
                    bag.add(storage.get());
                }

                for (Order order : bag) {
                    order.setStatus(OrderStatus.DELIVERING);
                    Formatter.logOrder(order.getId(), order.getStatus());
                }

                Thread.sleep(deliveryTime * 1000L);

                for (Order order : bag) {
                    order.setStatus(OrderStatus.COMPLETED);
                    Formatter.logOrder(order.getId(), order.getStatus());
                }
                bag.clear();
            }
            Formatter.logSystem("Курьер " + id + " закончил рабочий день");
        } catch (InterruptedException e) {
            Formatter.logSystem("Курьер " + id + " закончил рабочий день");
        }
    }

    public List<Order> getOrdersInBag() {
        return new ArrayList<>(bag);
    }
}