package com.tecno_comfenalco.pa.application.product.command.response;

import com.tecno_comfenalco.pa.domain.product.model.ProductModel;

public record GetProductByIdCommandResult(ProductModel product, String message) {

}
