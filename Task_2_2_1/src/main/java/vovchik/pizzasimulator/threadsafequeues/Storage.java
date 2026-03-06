package vovchik.pizzasimulator.threadsafequeues;

import vovchik.pizzasimulator.order.Order;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Storage implements ThreadSafeQueue {

    private final Queue<Order> completedOrders;
    private final int capacity;

    public Storage(int cap) {
        completedOrders = new LinkedList<>();
        capacity = cap;
    }

    public synchronized Order get() throws InterruptedException {
        while (completedOrders.isEmpty()) {
            wait();
        }
        Order order = completedOrders.remove();
        notifyAll();
        return order;
    }

    public synchronized void put(Order completedOrder) throws InterruptedException {
        while (getSize() == capacity) {
            wait();
        }
        completedOrders.add(completedOrder);
        notifyAll();
    }

    public synchronized List<Order> drainOrders() {
        List<Order> copy = new ArrayList<>(completedOrders);
        completedOrders.clear();
        notifyAll();
        return copy;
    }

    public synchronized boolean isEmpty() {
        return completedOrders.isEmpty();
    }

    private int getSize() {
        return completedOrders.size();
    }

}
