package com.tecno_comfenalco.pa.application.assetsResources.command.response;

import java.util.UUID;

public record UpdateAssetsCommandResult(
        UUID distributorId,
        UUID assetId,
        UUID productId,
        String message) {

}
