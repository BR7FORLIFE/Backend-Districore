package com.tecno_comfenalco.pa.application.inventory.dto.response;

import java.util.UUID;

public record DeleteInventoryResponseDto(
        UUID inventoryId,
        String message) {

}
