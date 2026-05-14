package com.tecno_comfenalco.pa.application.warehouse.command.actions;

import java.util.UUID;

public record GetWareHouseByIdCommand(
        UUID userDistributorId,
        UUID warehouseId) {

}
