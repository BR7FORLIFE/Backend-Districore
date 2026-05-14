package com.tecno_comfenalco.pa.application.warehouse.command.response;

import java.util.UUID;

public record DeleteLogicWareHouseCommandResult(
        UUID distributorId,
        UUID warehouseId,
        String message) {

}
