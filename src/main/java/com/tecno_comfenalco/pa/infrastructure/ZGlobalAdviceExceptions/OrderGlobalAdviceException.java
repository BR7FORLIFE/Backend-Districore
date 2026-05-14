package com.tecno_comfenalco.pa.infrastructure.ZGlobalAdviceExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tecno_comfenalco.pa.application.delivery.exceptions.InvalidDeliveryStatusOrderTransitionException;
import com.tecno_comfenalco.pa.application.orders.exceptions.InvalidOrderRequestTransitionException;
import com.tecno_comfenalco.pa.application.orders.exceptions.OrderAlreadyExistsException;
import com.tecno_comfenalco.pa.application.orders.exceptions.OrderExpirationException;
import com.tecno_comfenalco.pa.application.orders.exceptions.OrderNotFoundException;
import com.tecno_comfenalco.pa.application.orders.exceptions.UnprocessableOrderException;
import com.tecno_comfenalco.pa.application.orders.exceptions.UnprocessableOrderSatusException;
import com.tecno_comfenalco.pa.shared.utils.helper.ApiError;
import com.tecno_comfenalco.pa.shared.utils.helper.StaticError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class OrderGlobalAdviceException {

    @ExceptionHandler(InvalidOrderRequestTransitionException.class)
    public ResponseEntity<ApiError> handleInvalidOrderRequestTransition(
            InvalidOrderRequestTransitionException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidDeliveryStatusOrderTransitionException.class)
    public ResponseEntity<ApiError> handleInvalidDeliveryStatusOrder(
            InvalidDeliveryStatusOrderTransitionException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderNotFound(
            OrderNotFoundException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(OrderAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleOrderAlreadyExists(
            OrderAlreadyExistsException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(UnprocessableOrderException.class)
    public ResponseEntity<ApiError> handleUnprocessableOrder(
            UnprocessableOrderException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(UnprocessableOrderSatusException.class)
    public ResponseEntity<ApiError> handleUnprocessableOrderStatus(
            UnprocessableOrderException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }

    @ExceptionHandler(OrderExpirationException.class)
    public ResponseEntity<ApiError> handleOrderExpired(
            UnprocessableOrderException ex,
            HttpServletRequest request) {
        return StaticError.buildError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request);
    }
}
