package com.tecno_comfenalco.pa.application.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UpdateInventoryRequestDto(
        @Positive(message = "la cantidad debe ser positiva") @NotBlank(message = "la cantidad no debe ser nulo ni vacio!") Long quantity) {

}
