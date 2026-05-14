package com.tecno_comfenalco.pa.application.warehouse.command.response;

import java.util.UUID;

public record UpdateWareHouseCommandResult(
        UUID distributorId,
        UUID warehouseId,
        String message) {

}
