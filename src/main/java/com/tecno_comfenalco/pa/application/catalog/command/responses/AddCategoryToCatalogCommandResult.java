package com.tecno_comfenalco.pa.application.catalog.command.responses;

import java.util.UUID;

public record AddCategoryToCatalogCommandResult(UUID catalogId, UUID categoryId, String message) {

}
