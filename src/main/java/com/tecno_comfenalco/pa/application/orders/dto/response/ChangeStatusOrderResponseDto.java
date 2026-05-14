package com.tecno_comfenalco.pa.application.orders.dto.response;

import java.util.UUID;

public record ChangeStatusOrderResponseDto(
        UUID orderId,
        UUID distributorId,
        String message) {

}
