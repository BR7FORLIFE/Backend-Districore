package com.tecno_comfenalco.pa.infrastructure.orders.mapper;

import com.tecno_comfenalco.pa.domain.orders.model.OrderProductModel;
import com.tecno_comfenalco.pa.infrastructure.orders.entity.OrderProductDocument;

public class OrderProductMapper {

    public static OrderProductModel toDomain(OrderProductDocument document) {
        OrderProductModel model = OrderProductModel.createNew(
                document.getId(),
                document.getProductId(),
                document.getSku(),
                document.getQuantity(),
                document.getSubtotal(),
                document.getSubtotalIva(),
                document.getIva(),
                document.getTotal(),
                document.getUnit());

        return model;
    }

    public static OrderProductDocument toEntity(OrderProductModel model) {
        OrderProductDocument document = new OrderProductDocument();

        document.setId(model.getId());
        document.setProductId(model.getProductId());
        document.setSku(model.getSku());
        document.setQuantity(model.getQuantity());
        document.setSubtotal(model.getsubtotal());
        document.setSubtotalIva(model.getSubtotalIva());
        document.setIva(model.getIva());
        document.setTotal(model.getTotal());
        document.setUnit(model.getUnit());

        return document;
    }
}
