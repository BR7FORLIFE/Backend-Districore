package com.tecno_comfenalco.pa.application.assetsResources.command.response;

import com.tecno_comfenalco.pa.domain.assestsResources.model.AssestsResourceModel;

public record GetAssetsByIdCommandResult(
        AssestsResourceModel asset,
        String message) {

}
