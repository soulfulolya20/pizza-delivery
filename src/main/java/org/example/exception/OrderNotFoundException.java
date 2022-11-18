package org.example.exception;

public class OrderNotFoundException extends RuntimeException {

    private final Long orderId;

    public OrderNotFoundException(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String getMessage() {
        return "Order with id = " + orderId + " not found";
    }
}
