package com.tecno_comfenalco.pa.application.assetsResources.command.actions;

import java.util.UUID;

public record GetAssetsByIdCommand(
        UUID userDistributorId,
        UUID assetId) {

}
