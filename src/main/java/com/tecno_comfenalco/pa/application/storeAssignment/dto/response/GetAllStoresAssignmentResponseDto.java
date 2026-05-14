package com.tecno_comfenalco.pa.application.storeAssignment.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.store.models.StoreModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetAllStoresAssignmentResponseDto(
                List<StoreModel> stores,
                PaginationMeta meta,
                String message) {

}
