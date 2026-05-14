package com.tecno_comfenalco.pa.application.presales.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public record ListPresalesCommand(UUID userDistributorId, RequestParams params) {

}
