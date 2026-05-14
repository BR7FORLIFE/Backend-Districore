package com.tecno_comfenalco.pa.application.product.dto.request;


import com.tecno_comfenalco.pa.shared.enums.Unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EditProductRequestDto(@NotBlank(message = "El nombre no puede estar vacio o nulo!") String name,
        @NotBlank(message = "El sku no puede ser nulo!") String sku,
        @NotNull(message = "El precio del producto no puede ser nulo!") @Positive(message = "El precio del producto no puede ser negativo") Double price,
        Unit unit) {

}
