package com.tecno_comfenalco.pa.application.catalog.command.actions;

import java.util.UUID;

public record GetProductByCategoryCommand(UUID productId, UUID distributorId) {

}
