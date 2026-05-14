package com.tecno_comfenalco.pa.application.store.command.response;

import java.util.UUID;

public record RegisterStoreCommandResult(UUID storeId, UUID userId, String message) {

}
