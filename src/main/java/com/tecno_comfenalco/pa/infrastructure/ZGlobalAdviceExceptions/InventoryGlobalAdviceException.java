package com.tecno_comfenalco.pa.infrastructure.ZGlobalAdviceExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tecno_comfenalco.pa.application.inventory.exceptions.BadInventoryStockException;
import com.tecno_comfenalco.pa.application.inventory.exceptions.InventoryAlreadyExistsException;
import com.tecno_comfenalco.pa.application.inventory.exceptions.InventoryNotFoundException;
import com.tecno_comfenalco.pa.shared.utils.helper.ApiError;
import com.tecno_comfenalco.pa.shared.utils.helper.StaticError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class InventoryGlobalAdviceException {

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ApiError> handleInventoryNotFound(
            InventoryNotFoundException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(InventoryAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleInventoryAlreadyExists(
            InventoryAlreadyExistsException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(BadInventoryStockException.class)
    public ResponseEntity<ApiError> handleNegativeStock(
            BadInventoryStockException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }
}
