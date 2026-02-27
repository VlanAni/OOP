package vovchik.pizzasimulator.formatter;

import vovchik.pizzasimulator.order.OrderStatus;

public class Formatter {

    public static void logOrder(int orderId, OrderStatus status) {
        System.out.printf("[%d] [%s]\n", orderId, status.toString());
    }

    public static void logSystem(String message) {
        System.out.println(">>> " + message);
    }

}
