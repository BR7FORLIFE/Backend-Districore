package com.tecno_comfenalco.pa.application.auth.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequestDto(
        @NotBlank(message = "el username no puede ser vacio ni nulo!") String username,
        @NotBlank(message = "el password no puede ser vacio!") String password,
        @Email(message = "debe tener un formato correcto de email") String email) {
}
