package com.tecno_comfenalco.pa.application.delivery.dto.response;

import java.util.UUID;

public record RegisterDeliveryResponseDto(
        UUID deliveryId,
        UUID distributorId,
        String message) {

}
