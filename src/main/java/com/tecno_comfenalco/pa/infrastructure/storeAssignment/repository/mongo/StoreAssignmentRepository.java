package com.tecno_comfenalco.pa.infrastructure.storeAssignment.repository.mongo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.storeAssignment.entity.StoreAssignmentDocument;

public interface StoreAssignmentRepository extends MongoRepository<StoreAssignmentDocument, UUID> {
    List<StoreAssignmentDocument> findAllByStoreId(UUID StoreId);

    List<StoreAssignmentDocument> findAllByDistributorId(UUID DistributorId);

    boolean existsByStoreIdAndDistributorId(UUID StoreId, UUID DistributorId);
}
