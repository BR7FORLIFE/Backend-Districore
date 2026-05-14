package com.tecno_comfenalco.pa.infrastructure.category.mapper;

import java.util.List;

import com.tecno_comfenalco.pa.domain.category.models.CategoryModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductSummaryModel;
import com.tecno_comfenalco.pa.infrastructure.category.entity.CategoryEmbeddedEntity;
import com.tecno_comfenalco.pa.infrastructure.product.entity.ProductSummaryEmbeddedEntity;
import com.tecno_comfenalco.pa.infrastructure.product.mapper.ProductSummaryMapper;

public class CategoryEmbeddedMapper {
    public static CategoryModel toDomain(CategoryEmbeddedEntity entity) {
        if (entity == null)
            return null;

        List<ProductSummaryModel> products = (entity.getProducts() == null)
                ? List.of()
                : entity.getProducts().stream()
                        .map(ProductSummaryMapper::toDomain)
                        .toList();

        return CategoryModel.createNew(entity.getId(), entity.getName(), products);
    }

    public static CategoryEmbeddedEntity toEntity(CategoryModel model) {
        if (model == null)
            return null;

        List<ProductSummaryEmbeddedEntity> productEntities = (model.getProducts() == null)
                ? List.of()
                : model.getProducts().stream()
                        .map(ProductSummaryMapper::toEntity)
                        .toList();

        return new CategoryEmbeddedEntity(model.getId(), model.getName(), productEntities);
    }
}
