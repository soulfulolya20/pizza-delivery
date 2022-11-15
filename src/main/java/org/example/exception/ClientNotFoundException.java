package org.example.exception;

public class ClientNotFoundException extends RuntimeException{
    private final Long clientId;

    public ClientNotFoundException(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getMessage() {
        return "Profile with id = " + clientId + " not found";
    }
}
