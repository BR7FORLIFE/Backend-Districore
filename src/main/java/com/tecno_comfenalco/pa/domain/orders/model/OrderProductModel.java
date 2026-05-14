package com.tecno_comfenalco.pa.domain.orders.model;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.Unit;

public class OrderProductModel {
    private UUID id;
    private UUID productId;
    private String sku;
    private Long quantity;
    private Double subtotal;
    private Double subtotalIva; // precio + iva
    private Double iva;
    private Double total; // total en el producto individual IMPORTANTE para facturacion y sumas totales
    private Unit unit; // unidad de cada producto

    public static OrderProductModel createNew(
            UUID id,
            UUID productId,
            String sku,
            Long quantity,
            Double subtotal,
            Double subtotalIva,
            Double iva,
            Double total,
            Unit unit) {
        return new OrderProductModel(id, productId, sku, quantity, subtotal, subtotalIva, iva, total, unit);
    }

    public static OrderProductModel createDraft(
            UUID productId,
            String sku,
            Long quantity,
            Double subtotal,
            Double subtotalIva,
            Double iva,
            Double total,
            Unit unit) {
        return new OrderProductModel(UUID.randomUUID(), productId, sku, quantity, subtotal, subtotalIva, iva, total, unit);
    }

    public OrderProductModel(UUID id, UUID productId, String sku, Long quantity, Double subtotal, Double subtotalIva,
            Double iva,
            Double total, Unit unit) {
        this.id = id;
        this.productId = productId;
        this.sku = sku;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.subtotalIva = subtotalIva;
        this.iva = iva;
        this.total = total;
        this.unit = unit;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getSku() {
        return sku;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Double getsubtotal() {
        return subtotal;
    }

    public Double getSubtotalIva() {
        return subtotalIva;
    }

    public Double getIva() {
        return iva;
    }

    public Double getTotal() {
        return total;
    }

    public Unit getUnit() {
        return unit;
    }

}
