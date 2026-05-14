package com.tecno_comfenalco.pa.application.warehouse.dto.request;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

public record RegisterWareHouseRequestDto(
    String name,
    DirectionDto direction
) {
    
}
