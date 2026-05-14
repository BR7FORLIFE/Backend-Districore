package com.tecno_comfenalco.pa.application.orders.command.actions;

import java.util.UUID;

public record GetOrderByIdCommand(
        UUID orderId,
        UUID userId,
        UUID distributorId,
        String role) {

}
