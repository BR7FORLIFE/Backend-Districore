package com.tecno_comfenalco.pa.application.assetsResources.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record SubmitAssetsRequestDto(
        @NotBlank(message = "la id del producto no puede ser nulo ni vacio") UUID productId, UUID cloudinaryPublicId,
        @NotBlank(message = "la url de la imagen no puede ser nulo ni vacio") String url, String name, boolean isMain,
        String format, Double width, Double height) {

}
