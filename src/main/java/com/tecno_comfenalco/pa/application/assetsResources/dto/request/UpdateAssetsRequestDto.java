package com.tecno_comfenalco.pa.application.assetsResources.dto.request;

public record UpdateAssetsRequestDto(
        String url,
        String name,
        boolean isMain,
        String format,
        Double width,
        Double height) {

}
