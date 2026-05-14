package com.tecno_comfenalco.pa.application.assetsResources.dto.response;

import java.util.UUID;

public record SubmitAssetsResponseDto(
        UUID assestId,
        UUID distributorId,
        UUID productId,
        String message) {

}
