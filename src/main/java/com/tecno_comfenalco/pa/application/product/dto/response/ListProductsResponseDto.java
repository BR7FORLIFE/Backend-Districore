package com.tecno_comfenalco.pa.application.product.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.product.model.ProductModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record ListProductsResponseDto(List<ProductModel> products, PaginationMeta meta, String message) {

}
