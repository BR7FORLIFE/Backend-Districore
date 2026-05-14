package com.tecno_comfenalco.pa.application.store.command.storeBinding.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.BindingStatusEnum;

public record ChangeStatusBindingCommand(
                UUID userDistributorId,
                UUID bindingId,
                BindingStatusEnum bindingStatus) {

}
