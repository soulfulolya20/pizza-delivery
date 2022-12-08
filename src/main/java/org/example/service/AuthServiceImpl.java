package org.example.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.models.dto.AuthRequestDTO;
import org.example.models.dto.AuthResponseDTO;
import org.example.models.dto.RegisterRequestDTO;
import org.example.models.entity.UserEntity;
import org.example.service.api.AuthService;
import org.example.service.api.UserService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final Environment environment;

    @Override
    public AuthResponseDTO login(AuthRequestDTO requestDTO) {
        UserEntity userEntity = userService.findByLogin(requestDTO.getLogin());
        checkPassword(userEntity.getPassword(), requestDTO.getPassword());

        Algorithm algorithm = Algorithm.HMAC256(environment.getProperty("secret"));
        String token = JWT.create()
                .withClaim("userId", userEntity.getUserId())
                .withExpiresAt(Instant.now().plus(3, ChronoUnit.HOURS))
                .sign(algorithm);
        return new AuthResponseDTO(token);
    }

    @Override
    public void register(RegisterRequestDTO requestDTO) {
        userService.save(new UserEntity().setLogin(requestDTO.getLogin()).setPassword(encryptPassword(requestDTO.getPassword())));
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void checkPassword(String dbPass, String reqPass) {
        if (!passwordEncoder.matches(reqPass, dbPass)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
