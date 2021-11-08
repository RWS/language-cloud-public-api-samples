package com.controller;

import exception.ApiErrorResponse;
import exception.ApiForbiddenException;
import exception.ApiUnauthorizedException;
import exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiUnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleException(ApiUnauthorizedException exception) {
        return new ApiErrorResponse(exception.getMessage(), ErrorCode.UNAUTHORIZED, String.valueOf(401), null);
    }

    @ExceptionHandler(ApiForbiddenException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleException(ApiForbiddenException exception) {
        return new ApiErrorResponse(exception.getMessage(), ErrorCode.FORBIDDEN, String.valueOf(403), null);
    }
}