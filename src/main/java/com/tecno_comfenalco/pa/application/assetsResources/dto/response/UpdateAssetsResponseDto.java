package com.tecno_comfenalco.pa.application.assetsResources.dto.response;

import java.util.UUID;

public record UpdateAssetsResponseDto(
        UUID distributorId,
        UUID assetId,
        UUID productId,
        String message) {

}
