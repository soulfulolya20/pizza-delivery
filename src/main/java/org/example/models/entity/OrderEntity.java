package org.example.models.entity;

import org.example.models.enums.StatusType;

import java.time.LocalDateTime;

public record OrderEntity(
        Long orderId,
        Long clientId,
        Long courierId,
        Long dispatcherId,
        String note,
        StatusType status,
        LocalDateTime formedDttm,
        String address
) {
}
