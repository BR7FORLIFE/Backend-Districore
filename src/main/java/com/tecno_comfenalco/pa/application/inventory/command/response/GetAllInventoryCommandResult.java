package com.tecno_comfenalco.pa.application.inventory.command.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.inventory.models.InventoryModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetAllInventoryCommandResult(
        List<InventoryModel> inventories,
        PaginationMeta meta,
        String message) {

}
