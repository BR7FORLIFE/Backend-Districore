package com.tecno_comfenalco.pa.application.warehouse.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.warehouse.models.WareHouseModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetAllWareHousesResponseDto(
        List<WareHouseModel> warehouses,
        PaginationMeta meta,
        String message) {

}
