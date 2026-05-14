package com.tecno_comfenalco.pa.application.delivery.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public record ListAllDeliveryCommand(UUID userDistributorId, RequestParams params) {

}
