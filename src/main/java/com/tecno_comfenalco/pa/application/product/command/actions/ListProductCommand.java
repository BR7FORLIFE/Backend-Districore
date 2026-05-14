package com.tecno_comfenalco.pa.application.product.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public record ListProductCommand(UUID userDistributorId, RequestParams params) {

}
