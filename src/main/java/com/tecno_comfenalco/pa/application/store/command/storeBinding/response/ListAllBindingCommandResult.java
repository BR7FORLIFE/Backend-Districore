package com.tecno_comfenalco.pa.application.store.command.storeBinding.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.store.models.StoreBindingRequestModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record ListAllBindingCommandResult(
        List<StoreBindingRequestModel> bindings,
        PaginationMeta meta,
        String message) {

}
