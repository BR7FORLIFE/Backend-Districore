package com.tecno_comfenalco.pa.application.product.dto.request;

import com.tecno_comfenalco.pa.shared.enums.Unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegisterProductRequestDto(@NotBlank(message = "El sku no puede ser nulo o vacio!") String sku,
                @NotBlank(message = "El nombre no puede estar vacio o nulo!") String name,
                @NotNull(message = "El precio del producto no puede ser nulo!") @Positive(message = "El precio del producto no puede ser negativo") Double price,
                Unit unit) {

}
