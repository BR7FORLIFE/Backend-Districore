package com.tecno_comfenalco.pa.infrastructure.ZGlobalAdviceExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tecno_comfenalco.pa.application.catalog.exceptions.CatalogExistsException;
import com.tecno_comfenalco.pa.application.catalog.exceptions.CatalogNotFoundException;
import com.tecno_comfenalco.pa.application.catalog.exceptions.CategoryExistsException;
import com.tecno_comfenalco.pa.application.catalog.exceptions.CategoryNotFoundException;
import com.tecno_comfenalco.pa.application.catalog.exceptions.ProductExistsInCategoryException;
import com.tecno_comfenalco.pa.shared.utils.helper.ApiError;
import com.tecno_comfenalco.pa.shared.utils.helper.StaticError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CatalogGlobalAdviceException {

    @ExceptionHandler(CatalogExistsException.class)
    public ResponseEntity<ApiError> handleCatalogExistsException(
            CatalogExistsException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(CategoryExistsException.class)
    public ResponseEntity<ApiError> handleCategoryExistsException(
            CategoryExistsException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(CatalogNotFoundException.class)
    public ResponseEntity<ApiError> handleCatalogNotFound(
            CatalogNotFoundException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFound(
            CategoryNotFoundException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(ProductExistsInCategoryException.class)
    public ResponseEntity<ApiError> handleProductExistsIntoCategory(
            ProductExistsInCategoryException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }
}
