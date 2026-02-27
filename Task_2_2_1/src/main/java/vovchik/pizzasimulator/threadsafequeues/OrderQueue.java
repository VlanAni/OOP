package vovchik.pizzasimulator.threadsafequeues;

import vovchik.pizzasimulator.order.Order;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrderQueue {

    private final Queue<Order> newOrders;

    public OrderQueue() {
        newOrders = new LinkedList<>();
    }

    public synchronized Order get() throws InterruptedException {
        while (newOrders.isEmpty()) {
            wait();
        }
        Order order = newOrders.remove();
        notifyAll();
        return order;
    }

    public synchronized void put(Order newOrder) {
        newOrders.add(newOrder);
        notifyAll();
    }

    public synchronized List<Order> drainOrders() {
        List<Order> copy = new ArrayList<>(newOrders);
        newOrders.clear();
        notifyAll();
        return copy;
    }

}
