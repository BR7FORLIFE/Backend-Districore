package com.tecno_comfenalco.pa.application.store.dto.response;

import com.tecno_comfenalco.pa.domain.store.models.StoreModel;

public record MeStoreResponseDto(
        StoreModel store,
        String message) {

}
