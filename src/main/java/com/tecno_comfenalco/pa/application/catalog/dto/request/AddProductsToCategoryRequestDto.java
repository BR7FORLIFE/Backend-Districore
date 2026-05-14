package com.tecno_comfenalco.pa.application.catalog.dto.request;

import java.util.UUID;

import com.tecno_comfenalco.pa.domain.product.model.ProductSummaryModel;

public record AddProductsToCategoryRequestDto(UUID catalogId, UUID categoryId, ProductSummaryModel product) {

}
