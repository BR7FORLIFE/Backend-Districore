package com.tecno_comfenalco.pa.infrastructure.ZGlobalAdviceExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tecno_comfenalco.pa.application.assetsResources.exceptions.AssetsResourceAlreadyExistsException;
import com.tecno_comfenalco.pa.application.assetsResources.exceptions.AssetsResourceNotFoundException;
import com.tecno_comfenalco.pa.shared.utils.helper.ApiError;
import com.tecno_comfenalco.pa.shared.utils.helper.StaticError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class AssetsResourceGlobalAdviceException {

    @ExceptionHandler(AssetsResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleAssetsNotFound(
            AssetsResourceNotFoundException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(AssetsResourceAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAssetsAlreadyExists(
            AssetsResourceAlreadyExistsException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }
}
