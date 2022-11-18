package org.example.models.entity;

public record OrderEntity(
     Long orderId,
     Long clientId,
     Long courierId,
     Long dispatcherId,
     String note,
     Boolean isFormed,
     Boolean isComplete
) {
}
