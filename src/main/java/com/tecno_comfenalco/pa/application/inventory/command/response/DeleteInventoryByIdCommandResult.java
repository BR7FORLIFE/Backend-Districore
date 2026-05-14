package com.tecno_comfenalco.pa.application.inventory.command.response;

import java.util.UUID;

public record DeleteInventoryByIdCommandResult(
        UUID inventoryId,
        String message) {

}
