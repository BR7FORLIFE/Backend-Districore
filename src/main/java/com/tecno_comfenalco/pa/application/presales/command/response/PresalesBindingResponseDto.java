package com.tecno_comfenalco.pa.application.presales.command.response;

import java.time.Instant;
import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.BindingStatusEnum;

public record PresalesBindingResponseDto(
        UUID bindingId,
        UUID distributorId,
        BindingStatusEnum status,
        Instant createAt,
        String message) {

}
