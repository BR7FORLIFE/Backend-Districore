package com.tecno_comfenalco.pa.infrastructure.ZGlobalAdviceExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tecno_comfenalco.pa.application.delivery.exceptions.DeliveryAlreadyExistsException;
import com.tecno_comfenalco.pa.application.delivery.exceptions.DeliveryNotFoundException;
import com.tecno_comfenalco.pa.shared.utils.helper.ApiError;
import com.tecno_comfenalco.pa.shared.utils.helper.StaticError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class DeliveryGlobalAdviceException {

    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<ApiError> handleDeliveryNotFound(
            DeliveryNotFoundException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(DeliveryAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleDeliveryAlreadyExists(
            DeliveryAlreadyExistsException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }
}
