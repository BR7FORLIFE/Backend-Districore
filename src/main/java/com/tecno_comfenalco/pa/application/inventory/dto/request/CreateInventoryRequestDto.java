package com.tecno_comfenalco.pa.application.inventory.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateInventoryRequestDto(
                UUID productId,
                UUID warehouseId,
                @Positive(message = "la cantidad del producto debe ser positiva") @NotBlank(message = "la cantidad no puede ser nula ni vacia!") Long quantity) {

}
