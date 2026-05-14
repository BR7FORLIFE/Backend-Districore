package com.tecno_comfenalco.pa.infrastructure.orders.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tecno_comfenalco.pa.domain.orders.model.OrderModel;
import com.tecno_comfenalco.pa.domain.orders.model.OrderProductModel;
import com.tecno_comfenalco.pa.infrastructure.orders.entity.OrderDocument;
import com.tecno_comfenalco.pa.infrastructure.orders.entity.OrderProductDocument;

public class OrderMapper {

    public static OrderModel toDomain(OrderDocument document) {
        List<OrderProductModel> orderProductsDomain = document.getOrderProducts()
                .stream()
                .map(OrderProductMapper::toDomain)
                .collect(Collectors.toList());

        OrderModel model = OrderModel.createNew(
                document.getId(),
                document.getNumberOrder(),
                document.getDistributorId(),
                document.getStoreId(),
                document.getPresalesId(),
                orderProductsDomain,
                document.getPaidForm(),
                document.getDeliveryStatus(),
                document.getStatusOrder(),
                document.getDiscount(),
                document.getTotalGeneralIva(),
                document.getTotal(),
                document.getCreateAt(),
                document.getExpiration(),
                document.getUpdateAt());

        return model;
    }

    public static OrderDocument toEntity(OrderModel model) {
        List<OrderProductDocument> orderProductDocument = model.getOrderProducts()
                .stream()
                .map(OrderProductMapper::toEntity)
                .collect(Collectors.toList());

        OrderDocument document = new OrderDocument();

        document.setId(model.getId());
        document.setNumberOrder(model.getNumberOrder());
        document.setDistributorId(model.getDistributorId());
        document.setStoreId(model.getStoreId());
        document.setPresalesId(model.getPresalesId());
        document.setOrderProducts(orderProductDocument);
        document.setPaidForm(model.getPaidForm());
        document.setDeliveryStatus(model.getDeliveryStatus());
        document.setStatusOrder(model.getStatusOrder());
        document.setDiscount(model.getDiscount());
        document.setTotalGeneralIva(model.getTotalGeneralIva());
        document.setTotal(model.getTotal());
        document.setCreateAt(model.getCreateAt());
        document.setExpiration(model.getExpiration());
        document.setUpdateAt(model.getUpdateAt());

        return document;
    }
}
