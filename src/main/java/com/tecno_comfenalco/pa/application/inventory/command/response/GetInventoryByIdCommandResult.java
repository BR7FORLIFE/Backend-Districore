package com.tecno_comfenalco.pa.application.inventory.command.response;

import com.tecno_comfenalco.pa.domain.inventory.models.InventoryModel;

public record GetInventoryByIdCommandResult(
        InventoryModel inventory,
        String message) {

}
