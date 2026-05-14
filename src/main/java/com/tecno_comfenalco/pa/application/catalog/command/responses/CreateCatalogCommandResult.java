package com.tecno_comfenalco.pa.application.catalog.command.responses;

import com.tecno_comfenalco.pa.domain.catalog.models.CatalogModel;

public record CreateCatalogCommandResult(CatalogModel catalog, String message) {

}
