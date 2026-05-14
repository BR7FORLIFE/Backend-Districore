package com.tecno_comfenalco.pa.application.auth.dto.requests;

import java.util.Set;

public record EditUserRequestDto(String username, String password, Set<String> roles, boolean enabled) {
}
