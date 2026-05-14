package com.tecno_comfenalco.pa.application.warehouse.dto.response;

import java.util.UUID;

public record UpdateWareHouseResponseDto(
        UUID distributorId,
        UUID warehouseId,
        String message) {

}
