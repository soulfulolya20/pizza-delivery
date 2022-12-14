package org.example.models.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserEntity {

    private Long userId;

    private String phone;

    private String password;

    String firstName;

    String middleName;

    String lastName;
}
