package com.tecno_comfenalco.pa.application.catalog.dto.response;

import com.tecno_comfenalco.pa.domain.catalog.models.CatalogModel;

public record CreateCatalogResponseDto(CatalogModel catalog, String message) {

}
