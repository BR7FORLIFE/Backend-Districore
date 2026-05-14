package com.tecno_comfenalco.pa.application.product.events;

import java.util.UUID;

public record ProductDeletedEvent(
        UUID id,
        UUID distributorId) {

}
