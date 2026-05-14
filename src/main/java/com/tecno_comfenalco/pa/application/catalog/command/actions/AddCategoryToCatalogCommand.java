package com.tecno_comfenalco.pa.application.catalog.command.actions;

import java.util.UUID;

public record AddCategoryToCatalogCommand(UUID userDistributorId, UUID catalogId, String name) {

}
