package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.ErrorInfo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ErrorInfo processException(Exception e, HttpServletResponse response) {
        log.error("Unexpected error", e);
        response.setStatus(500);
        return new ErrorInfo(e.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ErrorInfo processException(ResponseStatusException e, HttpServletResponse response) {
        log.error("{}", e.getMessage(), e);
        response.setStatus(e.getStatus().value());
        return new ErrorInfo(e.getMessage());
    }
}
