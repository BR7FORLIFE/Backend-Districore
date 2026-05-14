package com.tecno_comfenalco.pa.application.store.dto.storeBinding.request;

import jakarta.validation.constraints.NotBlank;

public record SendBindingStoreRequestDto(
        @NotBlank(message = "el nit no puede ser nulo ni vacio") String nit,
        @NotBlank(message = "el nombre de la tienda no puede ser nulo ni vacio!") String tempName) {

}
