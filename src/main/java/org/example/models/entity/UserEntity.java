package org.example.models.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserEntity {

    private Long userId;

    private String login;

    private String password;
}
