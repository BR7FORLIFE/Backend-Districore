package com.tecno_comfenalco.pa.application.catalog.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.catalog.models.CatalogModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetCatalogResponseDto(
                List<CatalogModel> catalogs,
                PaginationMeta meta,
                String message) {

}
