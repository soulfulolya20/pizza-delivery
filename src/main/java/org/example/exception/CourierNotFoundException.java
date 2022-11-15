package org.example.exception;

public class CourierNotFoundException extends RuntimeException {
    private final Long courierId;


    public CourierNotFoundException(Long courierId) {
        this.courierId = courierId;
    }

    @Override
    public String getMessage() {
        return "Courier with id = " + courierId + " not found";
    }
}
