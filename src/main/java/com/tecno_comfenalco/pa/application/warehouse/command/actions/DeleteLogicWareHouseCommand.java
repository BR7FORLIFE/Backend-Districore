package com.tecno_comfenalco.pa.application.warehouse.command.actions;

import java.util.UUID;

public record DeleteLogicWareHouseCommand(
        UUID userDistributorId,
        UUID warehouseId) {

}
