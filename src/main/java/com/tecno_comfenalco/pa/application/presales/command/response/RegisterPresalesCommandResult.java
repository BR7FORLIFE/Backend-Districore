package com.tecno_comfenalco.pa.application.presales.command.response;

import java.util.UUID;

public record RegisterPresalesCommandResult(UUID presalesId, UUID distributorId, UUID userId, String message) {

}
