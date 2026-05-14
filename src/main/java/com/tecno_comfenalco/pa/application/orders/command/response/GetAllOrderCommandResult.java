package com.tecno_comfenalco.pa.application.orders.command.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.orders.model.OrderModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetAllOrderCommandResult(
        List<OrderModel> orders,
        PaginationMeta meta,
        String message) {

}
