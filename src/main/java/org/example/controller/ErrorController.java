package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo processException(Exception e) {
        log.error("Unexpected error", e);
        return new ErrorInfo(e.getMessage());
    }
}
