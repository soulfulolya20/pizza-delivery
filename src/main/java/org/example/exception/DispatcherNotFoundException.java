package org.example.exception;

public class DispatcherNotFoundException extends RuntimeException {
    private final Long dispatcherId;

    public DispatcherNotFoundException(Long dispatcherId) {
        this.dispatcherId = dispatcherId;
    }

    @Override
    public String getMessage() {
        return "Dispatcher with id = " + dispatcherId + " not found";
    }
}
