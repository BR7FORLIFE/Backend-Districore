package com.tecno_comfenalco.pa.application.inventory.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.inventory.models.InventoryModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetAllInventoryResponseDto(
        List<InventoryModel> inventories,
        PaginationMeta meta,
        String message) {

}
