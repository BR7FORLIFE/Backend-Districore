package com.tecno_comfenalco.pa.application.warehouse.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

public record UpdateWareHouseCommand(
        UUID userDistributorId,
        UUID warehouseId,
        String name,
        DirectionDto direction) {

}
