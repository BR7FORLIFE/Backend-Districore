package com.tecno_comfenalco.pa.application.inventory.dto.response;

import java.util.UUID;

public record UpdateInventoryResponseDto(
        UUID inventoryId,
        String message) {

}
