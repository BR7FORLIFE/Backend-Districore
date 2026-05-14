package com.tecno_comfenalco.pa.infrastructure.ZGlobalAdviceExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tecno_comfenalco.pa.application.presales.exceptions.PresalesAlreadyExistsException;
import com.tecno_comfenalco.pa.application.presales.exceptions.PresalesNotFoundException;
import com.tecno_comfenalco.pa.shared.utils.helper.ApiError;
import com.tecno_comfenalco.pa.shared.utils.helper.StaticError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class PresalesGlobalAdviceException {

    @ExceptionHandler(PresalesAlreadyExistsException.class)
    public ResponseEntity<ApiError> handlePresalesExistsException(
            PresalesAlreadyExistsException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(PresalesNotFoundException.class)
    public ResponseEntity<ApiError> handlePresalesNotFoundException(
            PresalesNotFoundException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }
}
