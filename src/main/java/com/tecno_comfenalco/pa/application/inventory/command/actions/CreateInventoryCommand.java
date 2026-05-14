package com.tecno_comfenalco.pa.application.inventory.command.actions;

import java.util.UUID;

public record CreateInventoryCommand(
                UUID userDistributorId,
                UUID warehouseId,
                UUID productId,
                Long quantity) {

}
