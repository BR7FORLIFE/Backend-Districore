package com.tecno_comfenalco.pa.application.store.command.storeBinding.response;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.BindingStatusEnum;

public record ChangeStatusBindingCommandResult(
                UUID bindingId,
                BindingStatusEnum status,
                String code,
                String message) {

}
