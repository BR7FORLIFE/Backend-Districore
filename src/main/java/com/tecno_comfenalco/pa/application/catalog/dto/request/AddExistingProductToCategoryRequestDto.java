package com.tecno_comfenalco.pa.application.catalog.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AddExistingProductToCategoryRequestDto(@NotNull(message = "catalog ID is required") UUID productId) {

}
