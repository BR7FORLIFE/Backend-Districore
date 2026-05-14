package com.tecno_comfenalco.pa.domain.catalog.models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.category.models.CategoryModel;

public class CatalogModel {
    private UUID id;
    private UUID distributorId;
    private String catalogCode;
    private String name;
    private Instant createAt;
    private Instant updateAt;
    private List<CategoryModel> categories;

    public CatalogModel(UUID id, UUID distributorId, String catalogCode, String name, Instant createAt,
            Instant updateAt, List<CategoryModel> categories) {
        this.id = id;
        this.distributorId = distributorId;
        this.catalogCode = catalogCode;
        this.name = name;
        this.categories = categories;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static CatalogModel createDraft(UUID distributorId, String catalogCode, String name,
            List<CategoryModel> categories) {
        return new CatalogModel(UUID.randomUUID(), distributorId, catalogCode, name, Instant.now(), Instant.now(),
                categories);
    }

    public static CatalogModel createNew(UUID id, UUID distributorId, String catalogCode, String name,
            Instant createAt, Instant updateAt, List<CategoryModel> categories) {
        return new CatalogModel(id, distributorId, catalogCode, name, createAt, updateAt, categories);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public UUID getDistributorId() {
        return distributorId;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}
