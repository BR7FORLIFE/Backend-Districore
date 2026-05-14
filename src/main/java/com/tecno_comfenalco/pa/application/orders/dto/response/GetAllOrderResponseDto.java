package com.tecno_comfenalco.pa.application.orders.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.orders.model.OrderModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetAllOrderResponseDto(
        List<OrderModel> orders,
        PaginationMeta meta,
        String message) {

}
