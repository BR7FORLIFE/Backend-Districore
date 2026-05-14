package com.tecno_comfenalco.pa.application.store.command.storeBinding.response;

import java.time.Instant;
import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.BindingStatusEnum;

public record ReceiveAceptationByStoreCommandResult(
                UUID bindingId,
                UUID distributorId,
                String nit,
                BindingStatusEnum status,
                boolean isConsumed,
                Instant consumedAt,
                String message) {

}
