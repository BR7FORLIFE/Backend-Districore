package com.tecno_comfenalco.pa.application.inventory.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public record GetAllInventoryCommand(
        UUID userDistributorId,
        RequestParams params) {

}
