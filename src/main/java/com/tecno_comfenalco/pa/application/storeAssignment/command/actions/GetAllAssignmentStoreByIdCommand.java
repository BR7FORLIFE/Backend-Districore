package com.tecno_comfenalco.pa.application.storeAssignment.command.actions;

import java.util.UUID;

public record GetAllAssignmentStoreByIdCommand(
        UUID userDistributorId, UUID distributorId, UUID storeId) {

}
