package com.tecno_comfenalco.pa.application.presales.command.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.presales.model.PresalesModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record ListPresalesCommandResult(List<PresalesModel> presalesModels, PaginationMeta meta, String message) {

}
