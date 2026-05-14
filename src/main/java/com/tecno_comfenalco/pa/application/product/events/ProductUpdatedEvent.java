package com.tecno_comfenalco.pa.application.product.events;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.Unit;

//modelamos el evento que sera traladado o transportado entre publishers y consumers
public record ProductUpdatedEvent(
        UUID id,
        UUID distributorId,
        String sku,
        String name,
        Unit unit,
        Double price) {

}
