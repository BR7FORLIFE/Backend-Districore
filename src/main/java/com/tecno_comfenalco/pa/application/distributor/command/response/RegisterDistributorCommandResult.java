package com.tecno_comfenalco.pa.application.distributor.command.response;

import java.util.UUID;

public record RegisterDistributorCommandResult(UUID distributorId, UUID userId, String message) {

}
