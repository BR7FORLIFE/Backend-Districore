package com.tecno_comfenalco.pa.application.orders.dto.request;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.orders.RequestStatusOrderEnum;

public record ChangeStatusOrderRequestDto(
        UUID orderId,
        RequestStatusOrderEnum orderStatus) {

}
