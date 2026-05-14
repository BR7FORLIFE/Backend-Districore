package com.tecno_comfenalco.pa.infrastructure.inventory.repository.mongo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.inventory.entity.InventoryDocument;

public interface InventoryRepository extends MongoRepository<InventoryDocument, UUID> {
    Optional<InventoryDocument> findByIdAndDistributorId(UUID Id, UUID DistributorId);

    Page<InventoryDocument> findByDistributorId(UUID distributorId, Pageable pageable);

    void deleteByIdAndDistributorId(UUID Id, UUID DistributorId);

    List<InventoryDocument> findByDistributorIdAndProductIdIn(UUID DistributorId, List<UUID> productsIds);
}
