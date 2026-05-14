package com.tecno_comfenalco.pa.application.inventory.dto.response;

import java.util.UUID;

public record CreateInventoryResponseDto(
                UUID inventoryId,
                UUID warehouseId,
                UUID distributorId,
                UUID productId,
                String message) {

}
