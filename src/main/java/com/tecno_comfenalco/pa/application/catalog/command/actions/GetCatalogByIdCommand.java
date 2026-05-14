package com.tecno_comfenalco.pa.application.catalog.command.actions;

import java.util.UUID;

public record GetCatalogByIdCommand(
                UUID userId,
                UUID distributorId,
                UUID catalogId,
                String role) {

}
