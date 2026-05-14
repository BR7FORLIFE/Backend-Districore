package com.tecno_comfenalco.pa.application.delivery.command.response;

import com.tecno_comfenalco.pa.domain.delivery.model.DeliveryModel;

public record MeDeliveryCommandResult(
        DeliveryModel delivery,
        String message) {

}
