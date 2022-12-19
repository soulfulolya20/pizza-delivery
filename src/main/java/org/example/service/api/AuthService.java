package org.example.service.api;

import org.example.models.dto.AuthRequestDTO;
import org.example.models.dto.AuthResponseDTO;
import org.example.models.dto.ProfileDTO;
import org.example.models.dto.RegisterRequestDTO;
import org.example.models.entity.UserEntity;
import org.example.models.enums.ScopeType;

public interface AuthService {

    AuthResponseDTO login(AuthRequestDTO requestDTO);

    void register(RegisterRequestDTO requestDTO);

    ProfileDTO getProfile();

    void checkRoles(ScopeType[] roles, String accessToken);

    Boolean isCourier();
}
