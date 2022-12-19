package org.example.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.example.models.dto.UserAdminPageDTO;
import org.example.models.entity.UserEntity;
import org.example.models.enums.ScopeType;
import org.example.repository.UserRepository;
import org.example.service.api.CourierService;
import org.example.service.api.DispatcherService;
import org.example.service.api.UserService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CourierService courierService;

    private final DispatcherService dispatcherService;

    private final Environment environment;

    @Override
    public UserEntity findByLogin(String login) {
        return userRepository.findByPhone(login);
    }

    @Override
    public UserEntity save(UserEntity entity) {
        userRepository.save(entity);
        return userRepository.findByPhoneWithoutPassword(entity.getPhone());
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public UserEntity getCurrentUser() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return null;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");

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
        return getById(userId);
    }

    @Override
    public Boolean isAdmin(Long userId) {
        return userRepository.isAdmin(userId);
    }

    @Override
    public UserAdminPageDTO getUserAdminForm(String phone) {
        UserAdminPageDTO user = userRepository.findByPhoneForAdmin(phone);
        if (user == null) {
            return null;
        }
        List<ScopeType> roles = new ArrayList<>();
        roles.add(ScopeType.CLIENT);
        if (courierService.isCourier(user.getUserId())) {
            roles.add(ScopeType.COURIER);
        }
        if (dispatcherService.isDispatcher(user.getUserId())) {
            roles.add(ScopeType.DISPATCHER);
        }
        user.setRoles(roles);

        return user;
    }

    @Override
    public void setAdmin(Long userId) {
        userRepository.setAdmin(userId);
    }
}
