package com.tecno_comfenalco.pa.application.orders.command.response;

import com.tecno_comfenalco.pa.domain.orders.model.OrderModel;

public record GetOrderByIdCommandResult(
        OrderModel order,
        String message) {

}
