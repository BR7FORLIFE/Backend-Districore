package com.tecno_comfenalco.pa.application.auth.command.actions;

import java.util.UUID;

public record EditUserCommand(UUID userId, String username, String password) {

}
