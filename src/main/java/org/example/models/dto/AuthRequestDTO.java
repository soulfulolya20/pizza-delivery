package org.example.models.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthRequestDTO {

    @NotNull
    private String login;

    @NotNull
    private String password;
}
