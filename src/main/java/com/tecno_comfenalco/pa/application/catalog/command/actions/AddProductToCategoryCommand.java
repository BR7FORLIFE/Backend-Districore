package com.tecno_comfenalco.pa.application.catalog.command.actions;

import java.util.UUID;

public record AddProductToCategoryCommand(
                UUID userDistributorId,
                UUID catalogId,
                UUID categoryId,
                UUID productId) {

}
