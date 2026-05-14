package com.tecno_comfenalco.pa.infrastructure.orders.repository.mongo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.orders.entity.OrderDocument;

public interface OrderRepository extends MongoRepository<OrderDocument, UUID> {
    Optional<OrderDocument> findByIdAndDistributorId(UUID Id, UUID distributorId);

    Page<OrderDocument> findByDistributorId(UUID DistributorId, Pageable pageable);

    Page<OrderDocument> findByDistributorIdAndStoreId(UUID DistributorId, UUID storeId, Pageable pageable);

    Page<OrderDocument> findByDistributorIdAndPresalesId(UUID DistributorId, UUID presalesId, Pageable pageable);

}
