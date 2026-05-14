package com.tecno_comfenalco.pa.domain.inventory.service;

import com.tecno_comfenalco.pa.application.inventory.exceptions.BadInventoryStockException;
import com.tecno_comfenalco.pa.application.orders.draft.OrderProductDraft;
import com.tecno_comfenalco.pa.domain.inventory.models.InventoryModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductModel;

public class InventoryService {

    public static ProductModel verifyInventory(
            InventoryModel inventoryModel,
            ProductModel productModel,
            OrderProductDraft orderProductDraft) {
        if (inventoryModel.getQuantity() >= orderProductDraft.quantity()) {
            return productModel;
        } else if (inventoryModel.getQuantity() < 0 || orderProductDraft.quantity() < 0) {
            throw new BadInventoryStockException();
        }

        return null;
    }
}
