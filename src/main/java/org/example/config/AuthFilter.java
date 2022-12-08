package org.example.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    @Value("${security.disabled.paths}")
    private String paths;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Токен не найден");
        }

        token = token.replace("Bearer ", "");
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(getEnvironment().getProperty("secret"));
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Токен невалидный");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        log.debug(path);

        return checkExclusions(path, excludeAuthPoints());
    }

    private boolean checkExclusions(String path, List<String> excludeList) {
        return excludeList.stream().anyMatch(s -> {
            if (s.contains("**")) {
                return path.startsWith(s.replace("**", ""));
            } else {
                return path.equals(s);
            }
        });
    }

    private List<String> excludeAuthPoints() {
        List<String> points = List.of(paths.split(","));
        return withContextPathPoints(points, getEnvironment());
    }

    private List<String> withContextPathPoints(List<String> list, Environment environment) {
        String contextPath = environment.getProperty("server.servlet.context-path");
        if (contextPath != null) {
            return list.stream()
                    .map(point -> String.format("%s%s", contextPath, point))
                    .collect(Collectors.toList());
        }
        return list;
    }
}
