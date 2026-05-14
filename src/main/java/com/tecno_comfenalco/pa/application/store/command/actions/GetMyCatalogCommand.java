package com.tecno_comfenalco.pa.application.store.command.actions;

import java.util.UUID;

public record GetMyCatalogCommand(
        UUID userStoreId,
        UUID distributorId,
        UUID catalogId) {

}
