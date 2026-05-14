package com.tecno_comfenalco.pa.application.store.command.response;

import com.tecno_comfenalco.pa.domain.store.models.StoreModel;

public record GetStoreByIdCommandResult(StoreModel store, String message) {

}
