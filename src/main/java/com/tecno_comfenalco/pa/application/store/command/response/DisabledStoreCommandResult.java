package com.tecno_comfenalco.pa.application.store.command.response;

import java.util.UUID;

public record DisabledStoreCommandResult(
        UUID userId,
        String message) {

}
