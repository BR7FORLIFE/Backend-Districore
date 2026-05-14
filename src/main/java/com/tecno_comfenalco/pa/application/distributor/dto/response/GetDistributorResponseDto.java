package com.tecno_comfenalco.pa.application.distributor.dto.response;

import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;

public record GetDistributorResponseDto(DistributorModel distributor, String message) {

}
