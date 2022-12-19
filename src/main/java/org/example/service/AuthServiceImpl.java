package org.example.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.models.dto.AuthRequestDTO;
import org.example.models.dto.AuthResponseDTO;
import org.example.models.dto.ProfileDTO;
import org.example.models.dto.RegisterRequestDTO;
import org.example.models.entity.ClientEntity;
import org.example.models.entity.ProfileRequest;
import org.example.models.entity.UserEntity;
import org.example.models.enums.ScopeType;
import org.example.service.api.AuthService;
import org.example.service.api.ClientService;
import org.example.service.api.CourierService;
import org.example.service.api.DispatcherService;
import org.example.service.api.UserService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final ClientService clientService;

    private final DispatcherService dispatcherService;

    private final CourierService courierService;

    private final PasswordEncoder passwordEncoder;

    private final Environment environment;

    @Override
    public AuthResponseDTO login(AuthRequestDTO requestDTO) {
        UserEntity userEntity = userService.findByLogin(requestDTO.getPhone());
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
        String code = environment.getProperty("code");
        UserEntity savedUser = userService.save(new UserEntity()
                .setPhone(requestDTO.getPhone())
                .setPassword(encryptPassword(requestDTO.getPassword()))
                .setFirstName(requestDTO.getFirstName())
                .setLastName(requestDTO.getLastName())
                .setMiddleName(requestDTO.getMiddleName()));
        if (code.equals(requestDTO.getCode())) {
            userService.setAdmin(savedUser.getUserId());
        }
        clientService.createClient(new ProfileRequest(savedUser.getUserId()));
    }

    @Override
    public ProfileDTO getProfile() {
        UserEntity user = userService.getCurrentUser();
        ClientEntity client = clientService.getClientByUserId(user.getUserId());
        return new ProfileDTO()
                .setUserId(user.getUserId())
                .setFirstName(client.firstName())
                .setLastName(client.lastName())
                .setMiddleName(client.middleName())
                .setPhone(user.getPhone())
                .setScope(List.of(ScopeType.CLIENT));
    }

    @Override
    public void checkRoles(ScopeType[] roles, String accessToken) {
        Long userId = getUserId(accessToken);
        boolean isAuth = false;

        for (ScopeType role : roles) {
            try {
                switch (role) {
                    case CLIENT -> {
                        clientService.getClientByUserId(userId);
                        isAuth = true;
                    }
                    case DISPATCHER -> {
                        dispatcherService.getDispatcherByUserId(userId);
                        isAuth = true;
                    }
                    case COURIER -> {
                        courierService.getCourierByUserId(userId);
                        isAuth = true;
                    }
                    case ADMIN -> isAuth = userService.isAdmin(userId);
                }
            } catch (Exception ignored) {
            }
            if (isAuth) {
                return;
            }
        }
    }

    @Override
    public Boolean isCourier() {
        UserEntity user = userService.getCurrentUser();
        return courierService.isCourier(user.getUserId());
    }

    @Override
    public Boolean isDispatcher() {
        UserEntity user = userService.getCurrentUser();
        return dispatcherService.isDispatcher(user.getUserId());
    }

    @Override
    public Boolean isAdmin() {
        UserEntity user = userService.getCurrentUser();
        return userService.isAdmin(user.getUserId());
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void checkPassword(String dbPass, String reqPass) {
        if (!passwordEncoder.matches(reqPass, dbPass)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    private Long getUserId(String accessToken) {
        String token = accessToken.replace("Bearer ", "");
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(environment.getProperty("secret"));
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Токен невалидный");
        }

        Long userId = decodedJWT.getClaim("userId").asLong();
        return userId;
    }
}
