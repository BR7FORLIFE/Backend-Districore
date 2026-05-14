package com.tecno_comfenalco.pa.application.presales.dto.response;

import java.util.UUID;

public record RegisterPresalesResponseDto(
        UUID presalesId,
        UUID distributorId,
        String message) {

}
