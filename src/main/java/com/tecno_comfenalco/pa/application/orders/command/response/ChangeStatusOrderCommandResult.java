package com.tecno_comfenalco.pa.application.orders.command.response;

import java.util.UUID;

public record ChangeStatusOrderCommandResult(
        UUID orderId,
        UUID distributorId,
        String message) {

}
