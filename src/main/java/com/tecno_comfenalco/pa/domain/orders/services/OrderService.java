package com.tecno_comfenalco.pa.domain.orders.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.tecno_comfenalco.pa.application.inventory.exceptions.BadInventoryStockException;
import com.tecno_comfenalco.pa.application.orders.draft.OrderProductDraft;
import com.tecno_comfenalco.pa.application.orders.exceptions.InvalidOrderRequestTransitionException;
import com.tecno_comfenalco.pa.application.orders.exceptions.UnprocessableOrderSatusException;
import com.tecno_comfenalco.pa.application.product.exceptions.ProductNotExistsByIdsException;
import com.tecno_comfenalco.pa.application.product.exceptions.records.MissingProductInfo;
import com.tecno_comfenalco.pa.domain.orders.model.OrderModel;
import com.tecno_comfenalco.pa.domain.orders.model.OrderProductModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductModel;
import com.tecno_comfenalco.pa.shared.enums.orders.RequestStatusOrderEnum;

public class OrderService {

    public static void validateProduct(List<OrderProductDraft> productsClient,
            List<ProductModel> productExpected) {
        if (productExpected.size() != productsClient.size()) {

            Set<UUID> foundIds = productExpected.stream()
                    .map(ProductModel::getId)
                    .collect(Collectors.toSet());

            List<MissingProductInfo> missingProducts = productsClient.stream()
                    .filter(p -> !foundIds.contains(p.productId()))
                    .map(p -> {
                        return new MissingProductInfo(
                                p.productId(),
                                "Product not found!");
                    })
                    .toList();

            throw new ProductNotExistsByIdsException(missingProducts);
        }
    }

    public static OrderProductModel logicAndCreateSubOrder(ProductModel product, Double IVA_GOVERNMENT, Long quantity) {
        // validamos que el stock no sea negativo si alguno lo es la order no se ejecuta
        if (quantity < 0) {
            throw new BadInventoryStockException();
        }

        Double subtotal = formatToFourDecimals(quantity * product.getPrice());
        Double taxAmount = formatToFourDecimals(subtotal * IVA_GOVERNMENT);
        Double total = formatToFourDecimals(subtotal + taxAmount);

        OrderProductModel orderProduct = OrderProductModel.createDraft(
                product.getId(),
                product.getSku(),
                quantity,
                subtotal,
                taxAmount,
                IVA_GOVERNMENT,
                total,
                product.getUnit());

        return orderProduct;
    }

    public static Double formatToFourDecimals(Double value) {
        return BigDecimal.valueOf(value)
                .setScale(4, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public static OrderModel transitionedStateRequestEnum(OrderModel oldModel, RequestStatusOrderEnum orderStatus) {
        OrderModel order = null;

        switch (orderStatus) {
            case ACCEPT:
                oldModel.getStatusOrder().accept();
                order = createOrder(oldModel, orderStatus);
                break;

            case PENDING:
                throw new InvalidOrderRequestTransitionException(orderStatus);

            case PROBLEM:
                oldModel.getStatusOrder().problem();
                order = createOrder(oldModel, orderStatus);
                break;

            case REJECTED:
                oldModel.getStatusOrder().rejected();
                order = createOrder(oldModel, orderStatus);
                break;

            default:
                throw new UnprocessableOrderSatusException();
        }

        return order;
    }

    private static OrderModel createOrder(OrderModel oldModel, RequestStatusOrderEnum orderStatus) {
        return OrderModel.createNew(
                oldModel.getId(),
                oldModel.getNumberOrder(),
                oldModel.getDistributorId(),
                oldModel.getStoreId(),
                oldModel.getPresalesId(),
                oldModel.getOrderProducts(),
                oldModel.getPaidForm(),
                oldModel.getDeliveryStatus(),
                orderStatus,
                oldModel.getDiscount(),
                oldModel.getTotalGeneralIva(),
                oldModel.getTotal(),
                oldModel.getCreateAt(),
                oldModel.getExpiration(),
                Instant.now());
    }

}
