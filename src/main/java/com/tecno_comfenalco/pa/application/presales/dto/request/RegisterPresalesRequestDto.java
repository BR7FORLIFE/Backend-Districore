package com.tecno_comfenalco.pa.application.presales.dto.request;

import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterPresalesRequestDto(
        @NotBlank(message = "el username del preventista no puede ser nulo ni vacio") String username,
        @NotBlank(message = "el password no puede nulo ni vacio") String password,
        @NotBlank(message = "El nombre del preventista no debe ser nulo ni vacio!") String name,
        @NotBlank(message = "El numero de telefono no puede ser nulo ni vacio!") String phoneNumber,
        @Email(message = "El email debe tener un formato correcto!") String email,
        DocumentTypeEnum documentType,
        @NotNull(message = "El numero de documento de identidad no debe ser nulo!") String documentNumber) {
}