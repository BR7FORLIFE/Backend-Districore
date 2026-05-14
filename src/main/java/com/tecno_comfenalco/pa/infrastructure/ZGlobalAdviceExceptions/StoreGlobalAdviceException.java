package com.tecno_comfenalco.pa.infrastructure.ZGlobalAdviceExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tecno_comfenalco.pa.application.store.exceptions.InvalidStateBindingException;
import com.tecno_comfenalco.pa.application.store.exceptions.InvalidTransitionStateBindingException;
import com.tecno_comfenalco.pa.application.store.exceptions.StoreAlreadyExistsException;
import com.tecno_comfenalco.pa.application.store.exceptions.StoreBindingAlreadyExistsException;
import com.tecno_comfenalco.pa.application.store.exceptions.StoreBindingNotFoundException;
import com.tecno_comfenalco.pa.application.store.exceptions.StoreNotFoundException;
import com.tecno_comfenalco.pa.shared.utils.helper.ApiError;
import com.tecno_comfenalco.pa.shared.utils.helper.StaticError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class StoreGlobalAdviceException {

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<ApiError> handleStoreNotFound(
            StoreNotFoundException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(StoreAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleStoreAlreadyExists(
            StoreAlreadyExistsException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidTransitionStateBindingException.class)
    public ResponseEntity<ApiError> handleBindingStatusRequest(
            InvalidTransitionStateBindingException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(StoreBindingNotFoundException.class)
    public ResponseEntity<ApiError> handleStoreBindingNotFound(
            StoreBindingNotFoundException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(StoreBindingAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleStoreBindingAlreadyExists(
            StoreBindingAlreadyExistsException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidStateBindingException.class)
    public ResponseEntity<ApiError> handleStateNotFound(
            InvalidStateBindingException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }
}
