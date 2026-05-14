package com.tecno_comfenalco.pa.application.product.command.response;

import java.util.UUID;

public record RegisterProductCommandResult(UUID productId, String sku, String message) {

}
