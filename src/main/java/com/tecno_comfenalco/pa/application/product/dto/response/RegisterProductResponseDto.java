package com.tecno_comfenalco.pa.application.product.dto.response;

import java.util.UUID;

public record RegisterProductResponseDto(UUID productId, String sku, String message) {

}
