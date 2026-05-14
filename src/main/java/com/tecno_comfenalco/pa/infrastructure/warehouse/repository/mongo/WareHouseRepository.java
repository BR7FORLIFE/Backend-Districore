package com.tecno_comfenalco.pa.infrastructure.warehouse.repository.mongo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.warehouse.entity.WareHouseDocument;

public interface WareHouseRepository extends MongoRepository<WareHouseDocument, UUID> {
    Optional<WareHouseDocument> findByIdAndDistributorId(UUID Id, UUID DistributorId);

    Page<WareHouseDocument> findByDistributorId(UUID distributorId, Pageable pageable);

    Page<WareHouseDocument> findByDistributorIdAndNameContainingIgnoreCase(UUID distributorId, String name,
            Pageable pageable);
}
