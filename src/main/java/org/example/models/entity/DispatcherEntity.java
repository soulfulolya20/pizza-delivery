package org.example.models.entity;

public record DispatcherEntity(
        Long dispatcherId,
        String firstName,
        String middleName,
        String lastName,
        String phone
) {
}
