package com.tecno_comfenalco.pa.application.distributor.command.response;

import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;

public record GetDistributorByNITCommandResult(DistributorModel distributor, String message) {

}
