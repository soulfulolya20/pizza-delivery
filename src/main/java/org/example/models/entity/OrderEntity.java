package org.example.models.entity;

import java.time.LocalDateTime;

public record OrderEntity(
     Long orderId,
     Long clientId,
     Long courierId,
     Long dispatcherId,
     String note,
     Boolean isFormed,
     Boolean isComplete,
     LocalDateTime formedDttm
) {
}
