package com.tecno_comfenalco.pa.application.catalog.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public record ListCatalogCommand(
                UUID userId,
                UUID distributorId,
                String role,
                RequestParams params) {

}
