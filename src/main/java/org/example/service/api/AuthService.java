package org.example.service.api;

import org.example.models.dto.AuthRequestDTO;
import org.example.models.dto.AuthResponseDTO;
import org.example.models.dto.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO login(AuthRequestDTO requestDTO);

    void register(RegisterRequestDTO requestDTO);
}
