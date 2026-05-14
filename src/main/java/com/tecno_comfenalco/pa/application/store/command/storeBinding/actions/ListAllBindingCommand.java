package com.tecno_comfenalco.pa.application.store.command.storeBinding.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public record ListAllBindingCommand(UUID userDistributorId, RequestParams params) {

}
