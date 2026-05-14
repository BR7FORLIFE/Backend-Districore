package com.tecno_comfenalco.pa.application.orders.dto.response;

import com.tecno_comfenalco.pa.domain.orders.model.OrderModel;

public record GetOrderByIdResponseDto(
        OrderModel order,
        String message) {

}
