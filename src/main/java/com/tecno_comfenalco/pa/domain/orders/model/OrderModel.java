package com.tecno_comfenalco.pa.domain.orders.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.orders.DeliverStatusOrderEnum;
import com.tecno_comfenalco.pa.shared.enums.orders.OrderFormPaid;
import com.tecno_comfenalco.pa.shared.enums.orders.RequestStatusOrderEnum;

public class OrderModel {
    private UUID id;
    private UUID numberOrder; // numero de factura o emision de factura a nivel de negocio (facturas)
    private UUID distributorId;
    private UUID storeId;
    private UUID presalesId; // puede ser null si la tienda hace sus propios pedidos
    private List<OrderProductModel> orderProducts; // lista de productos dentro de un pedido (cantidad, producto, etc..)
    private OrderFormPaid paidForm;
    private DeliverStatusOrderEnum deliveryStatus; // estado de la entrega por parte del preventista
    private RequestStatusOrderEnum statusOrder; // el estado del pedido IMPORTANTE!
    private Double discount; // descuento de una factura si aplica
    private Double totalGeneralIva; // iva en general en la factura (no de productos individuales) si aplica
    private Double total;
    private Instant createAt;
    private Instant expiration; // fecha de expiracion de una factura para ser valida
    private Instant updateAt;

    public static OrderModel createNew(
            UUID id, 
            UUID numberOrder, 
            UUID distributorId, 
            UUID storeId, 
            UUID presalesId,
            List<OrderProductModel> orderProducts, 
            OrderFormPaid paidForm, 
            DeliverStatusOrderEnum deliveryStatus,
            RequestStatusOrderEnum statusOrder, 
            Double discount, 
            Double totalGeneralIva, 
            Double total, 
            Instant createAt,
            Instant expiration, 
            Instant updateAt) {
        return new OrderModel(
                id,
                numberOrder,
                distributorId,
                storeId,
                presalesId,
                orderProducts,
                paidForm,
                deliveryStatus,
                statusOrder,
                discount,
                totalGeneralIva,
                total,
                createAt,
                expiration,
                updateAt);
    }

    public static OrderModel createDraft(
            UUID distributorId,
            UUID storeId,
            UUID presalesId,
            List<OrderProductModel> orderProducts,
            OrderFormPaid paidForm,
            Double discount,
            Double totalGeneralIva,
            Double total,
            Instant expiration) {
        return new OrderModel(
                UUID.randomUUID(),
                UUID.randomUUID(), // generamos una id de factura
                distributorId,
                storeId,
                presalesId,
                orderProducts,
                paidForm,
                DeliverStatusOrderEnum.PENDING,
                RequestStatusOrderEnum.PENDING,
                discount,
                totalGeneralIva,
                total,
                Instant.now(),
                expiration,
                Instant.now());
    }

    public OrderModel(UUID id, UUID numberOrder, UUID distributorId, UUID storeId, UUID presalesId,
            List<OrderProductModel> orderProducts, OrderFormPaid paidForm, DeliverStatusOrderEnum deliveryStatus,
            RequestStatusOrderEnum statusOrder, Double discount, Double totalGeneralIva, Double total, Instant createAt,
            Instant expiration, Instant updateAt) {
        this.id = id;
        this.numberOrder = numberOrder;
        this.distributorId = distributorId;
        this.storeId = storeId;
        this.presalesId = presalesId;
        this.orderProducts = orderProducts;
        this.paidForm = paidForm;
        this.deliveryStatus = deliveryStatus;
        this.statusOrder = statusOrder;
        this.discount = discount;
        this.totalGeneralIva = totalGeneralIva;
        this.total = total;
        this.createAt = createAt;
        this.expiration = expiration;
        this.updateAt = updateAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getNumberOrder() {
        return numberOrder;
    }

    public UUID getDistributorId() {
        return distributorId;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public UUID getPresalesId() {
        return presalesId;
    }

    public List<OrderProductModel> getOrderProducts() {
        return orderProducts;
    }

    public OrderFormPaid getPaidForm() {
        return paidForm;
    }

    public DeliverStatusOrderEnum getDeliveryStatus() {
        return deliveryStatus;
    }

    public RequestStatusOrderEnum getStatusOrder() {
        return statusOrder;
    }

    public Double getDiscount() {
        return discount;
    }

    public Double getTotalGeneralIva() {
        return totalGeneralIva;
    }

    public Double getTotal() {
        return total;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

}
