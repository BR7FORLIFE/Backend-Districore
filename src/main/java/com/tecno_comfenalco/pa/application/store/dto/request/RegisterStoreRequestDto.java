package com.tecno_comfenalco.pa.application.store.dto.request;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterStoreRequestDto(
                @NotBlank(message = "el userid no puede ser nulo ni vacio!") UUID userId,
                @NotBlank(message = "el nombre no puede ser nulo ni vacio") String name,
                @NotBlank(message = "el nit no puede ser nulo ni vacio") String nit,
                @NotBlank(message = "el telefono no puede ser nulo ni vacio") String phoneNumber,
                @NotBlank(message = "el email no puede ser nulo ni vacio") @Email(message = "debe ser un formato de email valido") String email,
                DirectionDto direction) {

}
