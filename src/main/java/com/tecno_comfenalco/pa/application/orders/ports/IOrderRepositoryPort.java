package com.tecno_comfenalco.pa.application.orders.ports;

import java.util.Optional;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.orders.model.OrderModel;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

public interface IOrderRepositoryPort {
    OrderModel save(OrderModel orderModel);

    Optional<OrderModel> findByIdAndDistributorId(UUID orderId, UUID distributorId);

    PagedResult<OrderModel> findAllPaged(UUID distributorId, Integer page, Integer size, String sortBy,
            String direction);

    PagedResult<OrderModel> findAllPagedByAnotherRole(UUID distributorId, UUID userRoleId,
            Integer page, Integer size, String sortBy, String direction, String role);
}
