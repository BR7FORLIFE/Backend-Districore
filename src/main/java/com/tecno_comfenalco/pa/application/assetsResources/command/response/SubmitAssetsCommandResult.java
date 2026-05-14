package com.tecno_comfenalco.pa.application.assetsResources.command.response;

import java.util.UUID;

public record SubmitAssetsCommandResult(
        UUID assestId,
        UUID distributorId,
        UUID productId,
        String message) {

}
