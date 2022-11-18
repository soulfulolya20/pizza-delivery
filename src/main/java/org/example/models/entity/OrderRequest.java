package org.example.models.entity;

import javax.validation.constraints.NotNull;

public record OrderRequest(
        @NotNull
        Long clientId,
        @NotNull
        Long courierId,
        @NotNull
        Long dispatcherId,

        String note,

        Boolean isFormed,

        Boolean isComplete
) {
}
