package org.example.models.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.models.enums.ScopeType;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserAdminPageDTO {

    private Long userId;

    private String phone;

    String firstName;

    String middleName;

    String lastName;

    List<ScopeType> roles;
}
