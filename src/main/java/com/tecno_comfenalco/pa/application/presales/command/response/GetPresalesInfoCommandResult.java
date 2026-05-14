package com.tecno_comfenalco.pa.application.presales.command.response;

import com.tecno_comfenalco.pa.domain.presales.model.PresalesModel;

public record GetPresalesInfoCommandResult(PresalesModel presale, String message) {

}
