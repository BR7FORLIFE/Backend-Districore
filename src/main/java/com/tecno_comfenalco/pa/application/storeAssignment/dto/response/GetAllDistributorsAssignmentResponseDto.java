package com.tecno_comfenalco.pa.application.storeAssignment.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record GetAllDistributorsAssignmentResponseDto(
        List<DistributorModel> distributors,
        PaginationMeta meta,
        String message) {

}
