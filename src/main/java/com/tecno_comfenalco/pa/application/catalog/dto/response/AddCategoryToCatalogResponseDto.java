package com.tecno_comfenalco.pa.application.catalog.dto.response;

import java.util.UUID;

public record AddCategoryToCatalogResponseDto(UUID catalogId, UUID categoryId, String message) {

}
