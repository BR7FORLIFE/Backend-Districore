package com.tecno_comfenalco.pa.application.storeAssignment.command.response;

import com.tecno_comfenalco.pa.domain.store.models.StoreModel;

public record GetAllAssignmentStoreByIdCommandResult(
                StoreModel store,
                String message) {

}
