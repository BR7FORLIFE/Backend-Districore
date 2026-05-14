package com.tecno_comfenalco.pa.infrastructure.delivery.repository.mongo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.delivery.entity.DeliveryDocument;

public interface DeliveryRepository extends MongoRepository<DeliveryDocument, UUID> {
    boolean existsByDocumentNumber(String DocumentNumber);

    Page<DeliveryDocument> findByDistributorId(UUID DistributorId, Pageable pageable);

    Optional<DeliveryDocument> findByIdAndDistributorId(UUID Id, UUID DistributorId);

    Page<DeliveryDocument> findByDistributorIdAndNameContainingIgnoreCase(UUID distributorId, String name,
            Pageable pageable);

    Optional<DeliveryDocument> findByUserIdAndDistributorId(UUID Id, UUID DistributorId);
}
