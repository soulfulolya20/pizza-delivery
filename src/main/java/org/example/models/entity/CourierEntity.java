package org.example.models.entity;

public record CourierEntity(
        Long courierId,
        String firstName,
        String middleName,
        String lastName,
        String phone
) {
}
