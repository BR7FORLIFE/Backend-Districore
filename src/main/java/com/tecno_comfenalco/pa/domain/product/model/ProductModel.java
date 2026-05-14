package com.tecno_comfenalco.pa.domain.product.model;

import java.time.Instant;
import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.Unit;

public class ProductModel {
    private UUID id;
    private UUID distributorId;
    private String sku;
    private String name;
    private Unit unit;
    private Double price;
    private Instant createAt;
    private Instant updateAt;

    public ProductModel(UUID id, UUID distributorId, String sku, String name, Unit unit, Double price, Instant createAt,
            Instant updateAt) {
        this.id = id;
        this.distributorId = distributorId;
        this.name = name;
        this.sku = sku;
        this.unit = unit;
        this.price = price;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static ProductModel createNew(UUID id, UUID distributorId, String sku, String name, Unit unit,
            Double price, Instant createAt, Instant updateAt) {
        return new ProductModel(id, distributorId, sku, name, unit, price, createAt, updateAt);
    }

    public static ProductModel createDraft(UUID distributorId, String sku, String name, Unit unit, Double price) {
        return new ProductModel(UUID.randomUUID(), distributorId, sku, name, unit, price, Instant.now(), Instant.now());
    }

    public UUID getDistributorId() {
        return distributorId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getSku() {
        return sku;
    }

    public Double getPrice() {
        return price;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}
