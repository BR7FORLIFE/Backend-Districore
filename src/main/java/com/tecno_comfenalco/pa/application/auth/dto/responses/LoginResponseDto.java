package com.tecno_comfenalco.pa.application.auth.dto.responses;

import java.util.Set;
import java.util.UUID;

public record LoginResponseDto(String message, Set<String> role, UUID distributorId) {
}
