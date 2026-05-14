package com.tecno_comfenalco.pa.application.delivery.dto.response;

import com.tecno_comfenalco.pa.domain.delivery.model.DeliveryModel;

public record GetDeliveryByIdResponseDto(DeliveryModel delivery, String message) {

}
