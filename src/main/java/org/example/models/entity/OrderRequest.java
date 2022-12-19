package org.example.models.entity;

import org.example.models.enums.StatusType;

import javax.validation.constraints.NotNull;

public record OrderRequest(
        @NotNull
        Long clientId,
        Long courierId,
        Long dispatcherId,

        String note,

        StatusType status,
        String address
) {
}
