package com.tecno_comfenalco.pa.application.orders.dto.request;

import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.application.orders.draft.OrderProductDraft;

//rol PRESALES - STORE
public record CreateOrderRequestDto(
        UUID distributorId,
        UUID storeId,
        List<OrderProductDraft> products) {

}
