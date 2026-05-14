package com.tecno_comfenalco.pa.application.store.dto.response;

import java.util.UUID;

public record RegisterStoreResponseDto(
        UUID storeId, UUID userId, String message) {

}
