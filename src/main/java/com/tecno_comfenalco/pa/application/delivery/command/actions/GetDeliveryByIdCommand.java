package com.tecno_comfenalco.pa.application.delivery.command.actions;

import java.util.UUID;

public record GetDeliveryByIdCommand(UUID userDistributorId, UUID deliveryId) {

}
