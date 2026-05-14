package com.tecno_comfenalco.pa.application.distributor.dto.response;

import java.util.UUID;

public record RegisterDistributorResponseDto(
        UUID distributorId,
        UUID userId,
        String message) {

}
