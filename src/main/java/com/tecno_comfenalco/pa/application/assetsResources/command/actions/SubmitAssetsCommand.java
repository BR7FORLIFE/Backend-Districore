package com.tecno_comfenalco.pa.application.assetsResources.command.actions;

import java.util.UUID;

public record SubmitAssetsCommand(
        UUID userDistributorId,
        UUID productId,
        UUID cloudinaryPublicId,
        String url,
        String name,
        Boolean isMain,
        String format,
        Double width,
        Double height) {

}
