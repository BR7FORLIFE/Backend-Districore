package com.tecno_comfenalco.pa.infrastructure.product.mapper;

import com.tecno_comfenalco.pa.domain.product.model.ProductModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductSummaryModel;
import com.tecno_comfenalco.pa.infrastructure.product.entity.ProductDocument;
import com.tecno_comfenalco.pa.infrastructure.product.entity.ProductSummaryEmbeddedEntity;

public class ProductSummaryMapper {
    public static ProductSummaryModel toDomain(ProductSummaryEmbeddedEntity productSummaryEmbeddedEntity) {
        ProductSummaryModel productSummaryModel = ProductSummaryModel.createNew(productSummaryEmbeddedEntity.getId(),
                productSummaryEmbeddedEntity.getSku(), productSummaryEmbeddedEntity.getName(),
                productSummaryEmbeddedEntity.getUnit(), productSummaryEmbeddedEntity.getPrice());
        return productSummaryModel;
    }

    public static ProductSummaryEmbeddedEntity toEntity(ProductSummaryModel productSummaryModel) {
        ProductSummaryEmbeddedEntity productSummaryEmbeddedEntity = new ProductSummaryEmbeddedEntity(
                productSummaryModel.getId(), productSummaryModel.getSku(), productSummaryModel.getName(),
                productSummaryModel.getUnit(), productSummaryModel.getPrice());
        return productSummaryEmbeddedEntity;
    }

    public static ProductSummaryModel toSummary(ProductModel productModel) {
        ProductSummaryModel productSummaryModel = ProductSummaryModel.createNew(productModel.getId(),
                productModel.getSku(), productModel.getName(), productModel.getUnit(), productModel.getPrice());
        return productSummaryModel;
    }

    public static ProductSummaryModel toSummary(ProductDocument productDocument) {
        ProductSummaryModel productSummaryModel = ProductSummaryModel.createNew(productDocument.getId(),
                productDocument.getSku(), productDocument.getName(), productDocument.getUnit(),
                productDocument.getPrice());

        return productSummaryModel;
    }

    // public static ProductModel toDomain(ProductSummaryModel productSummaryModel)
    // {
    // ProductModel productModel =
    // ProductModel.createNew(productSummaryModel.getId(), null,
    // productSummaryModel.getSku(), productSummaryModel.getName(),
    // productSummaryModel.getUnit(),
    // productSummaryModel.getPrice());
    // return productModel;
    // }
}
