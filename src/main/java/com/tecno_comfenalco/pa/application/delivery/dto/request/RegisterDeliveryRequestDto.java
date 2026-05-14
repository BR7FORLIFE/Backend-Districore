package com.tecno_comfenalco.pa.application.delivery.dto.request;

import java.util.List;

import com.tecno_comfenalco.pa.domain.vehicle.model.VehicleSummaryModel;
import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;
import com.tecno_comfenalco.pa.shared.enums.LicenseTypeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDeliveryRequestDto(
        @NotBlank(message = "el username no puede ser nulo ni vacio") String username,
        @NotBlank(message = "la contraseña no puede ser nulo ni vacio") String password,
        @NotBlank(message = "el email no puede ser nulo ni vacio") String email,
        @NotBlank(message = "El nombre no debe ser nulo ni vacio!") String name,
        DocumentTypeEnum documentType,
        @NotNull(message = "El numero de documento no debe ser nulo!") String documentNumber,
        @NotBlank(message = "El telefono no debe ser nulo ni vacio!") String phoneNumber,
        @NotBlank(message = "El numero de licencia no debe ser nulo ni vacio!") String licenseNumber,
        LicenseTypeEnum licenseType, List<VehicleSummaryModel> vehicles) {

}
