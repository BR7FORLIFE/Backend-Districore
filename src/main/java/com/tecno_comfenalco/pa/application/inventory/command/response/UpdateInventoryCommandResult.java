package com.tecno_comfenalco.pa.application.inventory.command.response;

import java.util.UUID;

public record UpdateInventoryCommandResult(
        UUID inventoryId,
        String message) {

}
