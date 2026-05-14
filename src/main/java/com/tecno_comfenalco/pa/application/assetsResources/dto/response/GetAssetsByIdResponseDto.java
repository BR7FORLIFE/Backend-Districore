package com.tecno_comfenalco.pa.application.assetsResources.dto.response;

import com.tecno_comfenalco.pa.domain.assestsResources.model.AssestsResourceModel;

public record GetAssetsByIdResponseDto(
        AssestsResourceModel asset,
        String message) {

}
