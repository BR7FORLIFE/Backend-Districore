package com.tecno_comfenalco.pa.application.delivery.dto.response;

import com.tecno_comfenalco.pa.domain.delivery.model.DeliveryModel;

public record MeDeliveryResponseDto(
        DeliveryModel delivery,
        String message) {

}
