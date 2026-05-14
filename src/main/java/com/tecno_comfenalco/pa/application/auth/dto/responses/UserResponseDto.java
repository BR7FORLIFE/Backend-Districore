package com.tecno_comfenalco.pa.application.auth.dto.responses;

import java.util.Set;
import java.util.UUID;

import com.tecno_comfenalco.pa.application.auth.draft.UserDraft;

public record UserResponseDto(UserDraft user, String message) {
    public UserResponseDto(UUID id, String username, Set<String> roles, boolean enabled) {
        this(new UserDraft(id, username, roles, enabled), "Usuario obtenido exitosamente");
    }
}
