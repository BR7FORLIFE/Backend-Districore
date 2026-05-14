package com.tecno_comfenalco.pa.application.store.command.storeBinding.actions;

import java.util.UUID;

public record SendBindingCommand(
        UUID userPresalesId,
        UUID distributorId,
        String nit,
        String tempName) {

}
