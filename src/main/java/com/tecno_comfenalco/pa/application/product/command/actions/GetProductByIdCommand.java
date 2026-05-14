package com.tecno_comfenalco.pa.application.product.command.actions;

import java.util.UUID;

public record GetProductByIdCommand(UUID userDistributorId, UUID productId) {

}
