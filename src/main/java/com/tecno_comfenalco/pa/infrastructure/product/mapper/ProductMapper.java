package com.tecno_comfenalco.pa.infrastructure.product.mapper;

import com.tecno_comfenalco.pa.domain.product.model.ProductModel;
import com.tecno_comfenalco.pa.infrastructure.product.entity.ProductDocument;

public class ProductMapper {
    public static ProductDocument toEntity(ProductModel productModel) {
        ProductDocument productDocument = new ProductDocument();
        productDocument.setId(productModel.getId());
        productDocument.setDistributorId(productModel.getDistributorId());
        productDocument.setName(productModel.getName());
        productDocument.setSku(productModel.getSku());
        productDocument.setUnit(productModel.getUnit());
        productDocument.setPrice(productModel.getPrice());
        productDocument.setCreateAt(productModel.getCreateAt());
        productDocument.setUpdateAt(productModel.getUpdateAt());

        return productDocument;
    }

    public static ProductModel toDomain(ProductDocument productDocument) {
        ProductModel productModel = ProductModel.createNew(productDocument.getId(), productDocument.getDistributorId(),
                productDocument.getSku(), productDocument.getName(), productDocument.getUnit(),
                productDocument.getPrice(), productDocument.getCreateAt(), productDocument.getUpdateAt());

        return productModel;
    }
}
