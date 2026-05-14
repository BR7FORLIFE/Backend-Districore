package com.tecno_comfenalco.pa.application.warehouse.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public record GetAllWarehousesCommand(
        UUID userDistributorId,
        RequestParams params) {

}
