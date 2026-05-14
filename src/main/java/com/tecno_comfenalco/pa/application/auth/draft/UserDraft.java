package com.tecno_comfenalco.pa.application.auth.draft;

import java.util.Set;
import java.util.UUID;

public record UserDraft(UUID id, String username, Set<String> roles, boolean enabled) {

}
