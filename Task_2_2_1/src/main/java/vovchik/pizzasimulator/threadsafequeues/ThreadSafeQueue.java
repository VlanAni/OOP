package vovchik.pizzasimulator.threadsafequeues;

import vovchik.pizzasimulator.order.Order;

import java.util.List;

public interface ThreadSafeQueue {
    void put(Order order) throws InterruptedException;

    Order get() throws InterruptedException;

    List<Order> drainOrders();

    boolean isEmpty();
}
