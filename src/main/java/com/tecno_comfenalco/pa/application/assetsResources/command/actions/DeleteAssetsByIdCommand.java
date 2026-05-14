package com.tecno_comfenalco.pa.application.assetsResources.command.actions;

import java.util.UUID;

public record DeleteAssetsByIdCommand(
        UUID userDistributorId,
        UUID assetId) {

}
