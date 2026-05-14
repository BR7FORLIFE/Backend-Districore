package com.tecno_comfenalco.pa.application.delivery.usecases;

import java.util.UUID;

import com.tecno_comfenalco.pa.application.delivery.command.actions.GetDeliveryByIdCommand;
import com.tecno_comfenalco.pa.application.delivery.command.actions.ListAllDeliveryCommand;
import com.tecno_comfenalco.pa.application.delivery.command.actions.RegisterDeliveryCommand;
import com.tecno_comfenalco.pa.application.delivery.command.actions.UpdateDeliveryCommand;
import com.tecno_comfenalco.pa.application.delivery.command.response.GetDeliveryByIdCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.ListAllDeliveryCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.MeDeliveryCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.RegisterDeliveryCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.UpdateDeliveryCommandResult;

public interface DeliveryUseCase {
    RegisterDeliveryCommandResult registerDelivery(RegisterDeliveryCommand cmd);

    ListAllDeliveryCommandResult listAllDeliveries(ListAllDeliveryCommand cmd);

    GetDeliveryByIdCommandResult getDeliveryById(GetDeliveryByIdCommand cmd);

    UpdateDeliveryCommandResult updateDelivery(UpdateDeliveryCommand cmd);

    MeDeliveryCommandResult me(UUID userDeliveryId, UUID distributorId);
}
