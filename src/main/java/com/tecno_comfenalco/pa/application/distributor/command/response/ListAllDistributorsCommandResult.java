package com.tecno_comfenalco.pa.application.distributor.command.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record ListAllDistributorsCommandResult(List<DistributorModel> distributors, PaginationMeta meta ,String message) {

}
