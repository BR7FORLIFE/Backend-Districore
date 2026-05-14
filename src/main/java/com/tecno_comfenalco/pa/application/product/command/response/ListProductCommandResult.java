package com.tecno_comfenalco.pa.application.product.command.response;

import java.util.List;

import com.tecno_comfenalco.pa.domain.product.model.ProductModel;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

public record ListProductCommandResult(List<ProductModel> products, PaginationMeta meta, String message) {

}
