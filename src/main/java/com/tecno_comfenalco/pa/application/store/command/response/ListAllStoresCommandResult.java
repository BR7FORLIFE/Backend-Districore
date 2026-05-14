package com.tecno_comfenalco.pa.application.store.command.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.store.models.StoreModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record ListAllStoresCommandResult(List<StoreModel> stores, PaginationMeta meta, String message) {

}
