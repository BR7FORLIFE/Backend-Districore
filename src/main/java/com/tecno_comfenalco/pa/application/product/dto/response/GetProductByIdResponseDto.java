package com.tecno_comfenalco.pa.application.product.dto.response;

import com.tecno_comfenalco.pa.domain.product.model.ProductModel;

public record GetProductByIdResponseDto(ProductModel product, String message) {

}
