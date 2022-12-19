package org.example.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.service.api.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleAspect {

    private final AuthService authService;

    @Around("@annotation(org.example.aspect.AuthRole)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        assert servletRequestAttributes != null;

        HttpServletRequest request = servletRequestAttributes.getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AuthRole annotation = method.getAnnotation(AuthRole.class);
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (accessToken != null && !accessToken.isBlank()) {
            try {
                authService.checkRoles(annotation.roles(), accessToken);
            } catch (RuntimeException e) {
                throw new RuntimeException("Недостаточно прав");
            }
        }

        return joinPoint.proceed();
    }
}
