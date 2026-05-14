package com.tecno_comfenalco.pa.infrastructure.inventory.mapper;

import com.tecno_comfenalco.pa.domain.inventory.models.InventoryModel;
import com.tecno_comfenalco.pa.infrastructure.inventory.entity.InventoryDocument;

public class InventoryMapper {

    public static InventoryModel toDomain(InventoryDocument document) {
        InventoryModel model = InventoryModel.createNew(
                document.getId(),
                document.getDistributorId(),
                document.getProductId(),
                document.getWarehouseId(),
                document.getQuantity());

        return model;
    }

    public static InventoryDocument toEntity(InventoryModel model) {

        InventoryDocument document = new InventoryDocument();

        document.setId(model.getId());
        document.setDistributorId(model.getDistributorId());
        document.setProductId(model.getProductId());
        document.setWarehouseId(model.getWarehouseId());
        document.setQuantity(model.getQuantity());

        return document;
    }
}
