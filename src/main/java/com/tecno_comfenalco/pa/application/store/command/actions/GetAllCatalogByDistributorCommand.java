package com.tecno_comfenalco.pa.application.store.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public record GetAllCatalogByDistributorCommand(
                UUID distributorId,
                UUID userStoreId,
                RequestParams params) {

}
