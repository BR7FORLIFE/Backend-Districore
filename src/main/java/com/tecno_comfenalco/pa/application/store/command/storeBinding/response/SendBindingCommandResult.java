package com.tecno_comfenalco.pa.application.store.command.storeBinding.response;

import java.time.Instant;
import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.BindingStatusEnum;

public record SendBindingCommandResult(
                UUID bindingId,
                UUID distributorId,
                BindingStatusEnum status,
                Instant createAt,
                String message) {

}
