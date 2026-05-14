package com.tecno_comfenalco.pa.application.presales.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EditPresalesRequestDto(
        @NotBlank(message = "El nombre del preventista no debe ser nulo ni vacio!") String name,
        @NotBlank(message = "El numero de telefono no puede ser nulo ni vacio!") String phoneNumber) {
}
