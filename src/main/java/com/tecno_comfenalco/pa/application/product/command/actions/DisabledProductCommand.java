package com.tecno_comfenalco.pa.application.product.command.actions;

import java.util.UUID;

public record DisabledProductCommand(UUID userDistributorId, UUID productId) {

}
