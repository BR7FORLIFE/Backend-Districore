package com.tecno_comfenalco.pa.application.store.dto.response;

import com.tecno_comfenalco.pa.domain.store.models.StoreModel;

public record GetStoreyByIdResponseDto(StoreModel store, String message) {

}
