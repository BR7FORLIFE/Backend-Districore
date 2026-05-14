package com.tecno_comfenalco.pa.application.store.command.storeBinding.actions;

import java.util.UUID;

public record ReceiveAceptationByStoreCommand(
        UUID userStoreId,
        String token) {

}
