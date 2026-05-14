package com.tecno_comfenalco.pa.application.auth.command.response;

import java.util.Set;
import java.util.UUID;

public record LoginUserCommandResult(Set<String> role, UUID distributorId) {

}
