package com.tecno_comfenalco.pa.application.store.command.response;

import com.tecno_comfenalco.pa.domain.catalog.models.CatalogModel;

public record GetMyCatalogCommandResult(
        CatalogModel catalog,
        String message) {

}
