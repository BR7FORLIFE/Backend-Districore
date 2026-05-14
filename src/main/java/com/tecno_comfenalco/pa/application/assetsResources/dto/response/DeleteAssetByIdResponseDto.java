package com.tecno_comfenalco.pa.application.assetsResources.dto.response;

import java.util.UUID;

public record DeleteAssetByIdResponseDto(
        UUID assetId,
        String message) {

}
