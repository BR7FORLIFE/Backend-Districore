package com.tecno_comfenalco.pa.application.orders.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public record GetAllOrderCommand(
                UUID userId,
                UUID distributorId,
                String role,
                RequestParams params) {

}
