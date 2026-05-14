package com.tecno_comfenalco.pa.application.inventory.dto.response;

import com.tecno_comfenalco.pa.domain.inventory.models.InventoryModel;

public record GetInventoryByIdResponseDto(
        InventoryModel inventory,
        String message) {

}
