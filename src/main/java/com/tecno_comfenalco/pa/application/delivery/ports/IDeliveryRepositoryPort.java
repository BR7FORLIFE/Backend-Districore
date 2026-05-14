package com.tecno_comfenalco.pa.application.delivery.ports;

import java.util.Optional;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.delivery.model.DeliveryModel;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

public interface IDeliveryRepositoryPort {
    boolean existsByDocumentNumber(String documentNumber);

    DeliveryModel save(DeliveryModel deliveryModel);

    PagedResult<DeliveryModel> findAllPaged(UUID distributorId, String name, Integer page, Integer size, String sortBy,
            String direction);

    Optional<DeliveryModel> findByIdAndDistributorId(UUID id, UUID distributorId);

    Optional<DeliveryModel> findByUserIdAndDistributorId(UUID id, UUID distributorId);
}
