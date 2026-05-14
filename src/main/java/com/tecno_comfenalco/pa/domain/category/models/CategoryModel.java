package com.tecno_comfenalco.pa.domain.category.models;

import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.product.model.ProductSummaryModel;

public class CategoryModel {
    private UUID id;
    private String name;
    private List<ProductSummaryModel> products;

    public CategoryModel(UUID id, String name, List<ProductSummaryModel> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public static CategoryModel createNew(UUID id, String name, List<ProductSummaryModel> products) {
        return new CategoryModel(id, name, products);
    }

    public static CategoryModel createDraft(String name, List<ProductSummaryModel> products) {
        return new CategoryModel(UUID.randomUUID(), name, products);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProductSummaryModel> getProducts() {
        return products;
    }
}
