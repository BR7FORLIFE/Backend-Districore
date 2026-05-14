package com.tecno_comfenalco.pa.application.auth.command.actions;

public record LoginUserCommand(String username, String password, boolean rememberMe) {
}
