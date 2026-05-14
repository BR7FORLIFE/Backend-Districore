package com.tecno_comfenalco.pa.application.store.dto.response;

import com.tecno_comfenalco.pa.domain.catalog.models.CatalogModel;

public record GetMyCatalogResponseDto(
        CatalogModel catalog,
        String message) {

}
