package com.tecno_comfenalco.pa.application.assetsResources.command.response;

import java.util.UUID;

public record DeleteAssetsByIdCommandResult(
        UUID assetId,
        String message) {

}
