package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.models.dto.AuthRequestDTO;
import org.example.models.dto.AuthResponseDTO;
import org.example.models.dto.RegisterRequestDTO;
import org.example.service.api.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO requestDTO) {
        return authService.login(requestDTO);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequestDTO requestDTO) {
        authService.register(requestDTO);
    }
}
