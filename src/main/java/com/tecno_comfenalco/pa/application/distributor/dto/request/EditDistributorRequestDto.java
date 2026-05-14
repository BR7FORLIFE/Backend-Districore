package com.tecno_comfenalco.pa.application.distributor.dto.request;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditDistributorRequestDto(
                @NotNull(message = "El nombre de la distribuidora no puede ser nulo!") String name,
                @NotBlank(message = "El numero de telefono no puede ser nulo o vacio!") String phoneNumber,
                DirectionDto direction) {
}
