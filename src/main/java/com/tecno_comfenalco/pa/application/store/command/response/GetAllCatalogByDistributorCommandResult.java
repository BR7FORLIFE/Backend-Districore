package com.tecno_comfenalco.pa.application.store.command.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.catalog.models.CatalogModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetAllCatalogByDistributorCommandResult(
        List<CatalogModel> catalogs,
        PaginationMeta meta,
        String message) {

}
