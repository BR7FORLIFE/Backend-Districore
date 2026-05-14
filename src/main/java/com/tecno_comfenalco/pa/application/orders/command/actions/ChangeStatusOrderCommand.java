package com.tecno_comfenalco.pa.application.orders.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.orders.RequestStatusOrderEnum;

public record ChangeStatusOrderCommand(
        UUID orderId,
        UUID userDistributorId,
        RequestStatusOrderEnum orderStatus) {

}
