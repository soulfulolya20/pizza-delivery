package org.example.models.entity;

import javax.validation.constraints.NotNull;

public record DispatcherRequest(
        @NotNull
        String firstName,
        String middleName,
        @NotNull
        String lastName,
        @NotNull
        String phone
) {
}
