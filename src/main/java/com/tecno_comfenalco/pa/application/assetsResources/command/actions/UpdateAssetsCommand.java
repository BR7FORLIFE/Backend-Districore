package com.tecno_comfenalco.pa.application.assetsResources.command.actions;

import java.util.UUID;

public record UpdateAssetsCommand(
        UUID userDistributorId,
        UUID assetId,
        String url,
        String name,
        boolean isMain,
        String format,
        Double width,
        Double height) {

}
