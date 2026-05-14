package com.tecno_comfenalco.pa.application.orders.command.actions;

import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.application.orders.draft.OrderProductDraft;

public record CreateOrderCommand(
        UUID userId,
        UUID distributorId,
        String role,
        UUID storeId,
        List<OrderProductDraft> products) {

}
