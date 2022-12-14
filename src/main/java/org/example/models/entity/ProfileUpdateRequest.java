package org.example.models.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public record ProfileUpdateRequest (
        @NotNull
        Long clientId,
        @NotNull
        String firstName,

        String middleName,
        @NotNull
        String lastName,
        @NotNull
        String phone,
        @NotNull
        String clientAddress
){}
