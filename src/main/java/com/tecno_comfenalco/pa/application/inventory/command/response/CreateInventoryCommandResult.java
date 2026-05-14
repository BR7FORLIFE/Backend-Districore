package com.tecno_comfenalco.pa.application.inventory.command.response;

import java.util.UUID;

public record CreateInventoryCommandResult(
                UUID inventoryId,
                UUID warehouseId,
                UUID distributorId,
                UUID productId,
                String message) {

}
