package com.tecno_comfenalco.pa.application.presales.usecases;

import java.util.UUID;

import com.tecno_comfenalco.pa.application.presales.command.actions.EditPresalesCommand;
import com.tecno_comfenalco.pa.application.presales.command.actions.GetPresalesInfoCommand;
import com.tecno_comfenalco.pa.application.presales.command.actions.ListPresalesCommand;
import com.tecno_comfenalco.pa.application.presales.command.actions.RegisterPresalesCommand;
import com.tecno_comfenalco.pa.application.presales.command.response.EditPresalesCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.GetPresalesByIdCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.GetPresalesInfoCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.ListPresalesCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.RegisterPresalesCommandResult;

public interface PresalesUseCase {
    RegisterPresalesCommandResult registerPresales(RegisterPresalesCommand cmd);

    EditPresalesCommandResult editPresales(EditPresalesCommand cmd);

    ListPresalesCommandResult listPresales(ListPresalesCommand cmd);

    GetPresalesByIdCommandResult showPresales(UUID distributorId, UUID presalesId);

    GetPresalesInfoCommandResult getPresalesInfo(GetPresalesInfoCommand cmd);
}
