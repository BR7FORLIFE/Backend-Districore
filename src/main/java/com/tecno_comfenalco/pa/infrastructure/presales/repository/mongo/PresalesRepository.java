package com.tecno_comfenalco.pa.infrastructure.presales.repository.mongo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.presales.entity.PresalesDocument;

public interface PresalesRepository extends MongoRepository<PresalesDocument, UUID> {
    boolean existsByPhoneNumber(String PhoneNumber);

    Optional<PresalesDocument> findByIdAndDistributorId(UUID Id, UUID DistributorId);

    Page<PresalesDocument> findByDistributorId(UUID distributorId, Pageable pageable);

    Page<PresalesDocument> findByDistributorIdAndNameContainingIgnoreCase(UUID distributorId, String name,
            Pageable pageable);

    Optional<PresalesDocument> findByUserIdAndDistributorId(UUID UserId, UUID DistributorId);
}
