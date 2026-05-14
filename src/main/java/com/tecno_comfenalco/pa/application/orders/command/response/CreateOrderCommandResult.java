package com.tecno_comfenalco.pa.application.orders.command.response;

import java.time.Instant;
import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.orders.RequestStatusOrderEnum;

public record CreateOrderCommandResult(
                UUID orderId,
                UUID numberOrder,
                UUID distributorId,
                UUID storeId,
                RequestStatusOrderEnum statusOrder,
                Instant expiration,
                String message) {

}
