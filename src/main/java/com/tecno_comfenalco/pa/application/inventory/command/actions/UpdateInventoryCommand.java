package com.tecno_comfenalco.pa.application.inventory.command.actions;

import java.util.UUID;

public record UpdateInventoryCommand(
        UUID userDistributorId,
        UUID inventoryId,
        Long quantity) {

}
