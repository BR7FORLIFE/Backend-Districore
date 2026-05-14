package com.tecno_comfenalco.pa.application.store.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.catalog.models.CatalogModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetAllCatalogByDistributorResponseDto(
        List<CatalogModel> catalogs,
        PaginationMeta meta,
        String message) {

}
