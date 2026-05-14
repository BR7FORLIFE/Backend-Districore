package com.tecno_comfenalco.pa.application.store.dto.storeBinding.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.store.models.StoreBindingRequestModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record ListAllBindingStoreResponseDto(
        List<StoreBindingRequestModel> bindings,
        PaginationMeta meta,
        String message) {

}
