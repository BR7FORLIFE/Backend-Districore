package com.tecno_comfenalco.pa.application.presales.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.presales.model.PresalesModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record ListPresalesResponseDto(List<PresalesModel> presales, PaginationMeta meta, String message) {

}
