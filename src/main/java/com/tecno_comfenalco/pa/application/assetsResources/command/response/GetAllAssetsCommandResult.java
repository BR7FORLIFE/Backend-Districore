package com.tecno_comfenalco.pa.application.assetsResources.command.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.assestsResources.model.AssestsResourceModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetAllAssetsCommandResult(List<AssestsResourceModel> assets, PaginationMeta meta, String message) {

}
