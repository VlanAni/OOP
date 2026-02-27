package vovchik.pizzasimulator.order;

public class Order {

    private int id;
    private OrderStatus status;

    public Order(int initID, OrderStatus initStatus) {
        id = initID;
        status = initStatus;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int newId) {id = newId;}

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus newStatus) {
        status = newStatus;
    }

}
