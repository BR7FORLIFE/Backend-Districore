package com.tecno_comfenalco.pa.application.warehouse.dto.response;

import com.tecno_comfenalco.pa.domain.warehouse.models.WareHouseModel;

public record GetWareHouseByIdResponseDto(
    WareHouseModel warehouse,
    String message
) {
    
}
