package org.example.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {

    @NotNull
    private String phone;

    @NotNull
    private String password;
}
