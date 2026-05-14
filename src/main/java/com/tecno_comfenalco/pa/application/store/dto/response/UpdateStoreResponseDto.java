package com.tecno_comfenalco.pa.application.store.dto.response;

import java.util.UUID;

public record UpdateStoreResponseDto(
        UUID storeId,
        String message) {

}
