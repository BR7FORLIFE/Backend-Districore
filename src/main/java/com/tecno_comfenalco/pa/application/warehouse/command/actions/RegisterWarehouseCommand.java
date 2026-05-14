package com.tecno_comfenalco.pa.application.warehouse.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

public record RegisterWarehouseCommand(
                UUID userDistributorId,
                String name,
                DirectionDto direction) {

}
