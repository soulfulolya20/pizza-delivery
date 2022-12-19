package org.example.models.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public record ProfileRequest(
        @NotNull
        String firstName,

        String middleName,
        @NotNull
        String lastName,

        Long userId
){}
