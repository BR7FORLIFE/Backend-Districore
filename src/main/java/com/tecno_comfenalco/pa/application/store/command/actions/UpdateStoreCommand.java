package com.tecno_comfenalco.pa.application.store.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

public record UpdateStoreCommand(
        UUID storeId,
        String name,
        String phoneNumber,
        DirectionDto direction) {

}
