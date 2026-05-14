package com.tecno_comfenalco.pa.application.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddCategoryToCatalogRequestDto(
                @NotBlank(message = "el nombre no puede ser nulo ni vacio!") String name) {

}
