package com.tecno_comfenalco.pa.shared.utils.helper;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

public class StaticError {
    public static ResponseEntity<ApiError> buildError(
            HttpStatus status,
            String message,
            HttpServletRequest exchange) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                exchange.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
