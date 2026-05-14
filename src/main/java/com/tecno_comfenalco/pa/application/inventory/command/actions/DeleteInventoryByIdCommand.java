package com.tecno_comfenalco.pa.application.inventory.command.actions;

import java.util.UUID;

public record DeleteInventoryByIdCommand(
        UUID userDistributorId,
        UUID inventoryId) {

}
