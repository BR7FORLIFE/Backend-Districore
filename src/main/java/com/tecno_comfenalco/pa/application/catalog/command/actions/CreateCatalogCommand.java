package com.tecno_comfenalco.pa.application.catalog.command.actions;

import java.util.UUID;

public record CreateCatalogCommand(UUID userDistributorId, String name, String catalogCode) {

}
