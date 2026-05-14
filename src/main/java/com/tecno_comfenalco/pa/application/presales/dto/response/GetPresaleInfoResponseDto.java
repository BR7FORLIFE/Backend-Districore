package com.tecno_comfenalco.pa.application.presales.dto.response;

import com.tecno_comfenalco.pa.domain.presales.model.PresalesModel;

public record GetPresaleInfoResponseDto(PresalesModel presale, String message) {

}
