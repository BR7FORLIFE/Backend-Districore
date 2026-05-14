package com.tecno_comfenalco.pa.application.delivery.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.delivery.model.DeliveryModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record ListAllDeliveryResponseDto(List<DeliveryModel> deliveries, PaginationMeta meta, String message) {
    
}
