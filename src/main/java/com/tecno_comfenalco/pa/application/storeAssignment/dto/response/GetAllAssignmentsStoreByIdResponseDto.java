package com.tecno_comfenalco.pa.application.storeAssignment.dto.response;

import com.tecno_comfenalco.pa.domain.store.models.StoreModel;

public record GetAllAssignmentsStoreByIdResponseDto(
        StoreModel store,
        String message) {

}
