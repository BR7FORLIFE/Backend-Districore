package com.tecno_comfenalco.pa.application.distributor.usecase;

import java.util.UUID;

import com.tecno_comfenalco.pa.application.distributor.command.actions.EditDistributorCommand;
import com.tecno_comfenalco.pa.application.distributor.command.actions.ListAllDistributorsCommand;
import com.tecno_comfenalco.pa.application.distributor.command.actions.RegisterDistributorCommand;
import com.tecno_comfenalco.pa.application.distributor.command.response.EditDistributorCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.GetDistributorByIdCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.GetDistributorByNITCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.ListAllDistributorsCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.RegisterDistributorCommandResult;

public interface DistributorUseCase {
    RegisterDistributorCommandResult registerDistributor(RegisterDistributorCommand cmd);

    ListAllDistributorsCommandResult listAllDistributors(ListAllDistributorsCommand cmd);

    GetDistributorByIdCommandResult getDistributorById(UUID distributorId);

    GetDistributorByNITCommandResult getDistributorByNIT(String NIT);

    EditDistributorCommandResult editDistributor(EditDistributorCommand cmd);
}
