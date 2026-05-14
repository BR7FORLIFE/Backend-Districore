package com.tecno_comfenalco.pa.domain.inventory.models;

import java.util.UUID;

public class InventoryModel {
    private UUID id;
    private UUID distributorId;
    private UUID productId;
    private UUID warehouseId;
    private Long quantity;

    public static InventoryModel createNew(UUID id, UUID distributorId, UUID productId, UUID warehouseId,
            Long quantity) {
        return new InventoryModel(id, distributorId, productId, warehouseId, quantity);
    }

    public static InventoryModel createDraft(UUID distributorId, UUID productId, UUID warehouseId, Long quantity) {
        return new InventoryModel(UUID.randomUUID(), distributorId, productId, warehouseId, quantity);
    }

    public InventoryModel(UUID id, UUID distributorId, UUID productId, UUID warehouseId, Long quantity) {
        this.id = id;
        this.productId = productId;
        this.distributorId = distributorId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDistributorId() {
        return distributorId;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public Long getQuantity() {
        return quantity;
    }

}
