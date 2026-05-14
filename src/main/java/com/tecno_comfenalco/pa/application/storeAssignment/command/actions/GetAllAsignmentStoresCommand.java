package com.tecno_comfenalco.pa.application.storeAssignment.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public record GetAllAsignmentStoresCommand(
                UUID userDistributorId, UUID distributorId, RequestParams params) {

}
