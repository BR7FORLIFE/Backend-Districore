package com.tecno_comfenalco.pa.application.warehouse.command.response;

import com.tecno_comfenalco.pa.domain.warehouse.models.WareHouseModel;

public record GetWareHouseByIdCommandResult(
        WareHouseModel warehouse,
        String message) {

}
