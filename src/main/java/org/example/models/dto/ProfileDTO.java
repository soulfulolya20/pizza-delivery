package org.example.models.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.models.enums.ScopeType;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProfileDTO {

    private String phone;

    private String lastName;

    private String firstName;

    private String middleName;

    private Long userId;

    private List<ScopeType> scope;

}
