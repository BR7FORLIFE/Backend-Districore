package com.tecno_comfenalco.pa.infrastructure.ZGlobalAdviceExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tecno_comfenalco.pa.application.storeAssignment.exceptions.NoStoreAssigmentNotFoundException;
import com.tecno_comfenalco.pa.application.storeAssignment.exceptions.NoStoresAssignmentException;
import com.tecno_comfenalco.pa.application.storeAssignment.exceptions.NotDistributorsAssignmentException;
import com.tecno_comfenalco.pa.shared.utils.helper.ApiError;
import com.tecno_comfenalco.pa.shared.utils.helper.StaticError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class StoreAssignmentGlobalAdviceException {

    @ExceptionHandler(NotDistributorsAssignmentException.class)
    public ResponseEntity<ApiError> handleNotDistributorsAssigments(
            NotDistributorsAssignmentException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(NoStoresAssignmentException.class)
    public ResponseEntity<ApiError> handleNotStoresAssignments(
            NoStoresAssignmentException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(NoStoreAssigmentNotFoundException.class)
    public ResponseEntity<ApiError> handleStoreAssignmentNotFound(
            NoStoreAssigmentNotFoundException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }
}
