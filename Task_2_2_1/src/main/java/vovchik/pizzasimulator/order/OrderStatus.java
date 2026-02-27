package vovchik.pizzasimulator.order;

public enum OrderStatus {
    PENDING("The order is waiting for baking"),
    BAKING("Baking"),
    STORED("Stored"),
    DELIVERING("Delivering"),
    COMPLETED("The order has been delivered");

    private final String textStatus;

    OrderStatus(String s) {
        textStatus = s;
    }

    @Override
    public String toString() {
        return textStatus;
    }
}
