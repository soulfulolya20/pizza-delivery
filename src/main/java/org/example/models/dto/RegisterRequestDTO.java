package org.example.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequestDTO {

    private String phone;

    private String password;

    private String lastName;

    private String firstName;

    private String middleName;

    private String code;
}
