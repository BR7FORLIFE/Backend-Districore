package com.tecno_comfenalco.pa.application.warehouse.command.response;

import java.util.UUID;

public record RegisterWareHouseCommandResult(
        UUID warehouseId,
        UUID distributorId,
        String message) {

}
