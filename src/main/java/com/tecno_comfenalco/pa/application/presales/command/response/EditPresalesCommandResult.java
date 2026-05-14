package com.tecno_comfenalco.pa.application.presales.command.response;

import java.util.UUID;

public record EditPresalesCommandResult(UUID distributorId, UUID presalesId, String message) {

}
