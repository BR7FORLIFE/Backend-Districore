package com.tecno_comfenalco.pa.infrastructure.category.entity;

import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.infrastructure.product.entity.ProductSummaryEmbeddedEntity;

public class CategoryEmbeddedEntity {
    private UUID id;
    private String name;
    private List<ProductSummaryEmbeddedEntity> products;

    public CategoryEmbeddedEntity(UUID id, String name, List<ProductSummaryEmbeddedEntity> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProductSummaryEmbeddedEntity> getProducts() {
        return products;
    }
}
