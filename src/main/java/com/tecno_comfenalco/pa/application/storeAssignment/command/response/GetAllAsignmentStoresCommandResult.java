package com.tecno_comfenalco.pa.application.storeAssignment.command.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.store.models.StoreModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetAllAsignmentStoresCommandResult(
        List<StoreModel> stores,
        PaginationMeta meta,
        String message) {

}
