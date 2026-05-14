package com.tecno_comfenalco.pa.infrastructure.ZGlobalAdviceExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tecno_comfenalco.pa.application.product.exceptions.ProductExistsException;
import com.tecno_comfenalco.pa.application.product.exceptions.ProductNotExistsByIdsException;
import com.tecno_comfenalco.pa.application.product.exceptions.ProductNotFoundException;
import com.tecno_comfenalco.pa.shared.utils.helper.ApiError;
import com.tecno_comfenalco.pa.shared.utils.helper.StaticError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ProductGlobalAdviceException {

    @ExceptionHandler(ProductExistsException.class)
    public ResponseEntity<ApiError> handleProductExistsException(
            ProductExistsException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFoundException(
            ProductNotFoundException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(ProductNotExistsByIdsException.class)
    public ResponseEntity<ApiError> handleNotExistsProductsByIds(
            ProductNotExistsByIdsException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }
}
