package com.tecno_comfenalco.pa.application.distributor.command.response;

import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;

public record GetDistributorByIdCommandResult(DistributorModel distributor, String message) {

}
