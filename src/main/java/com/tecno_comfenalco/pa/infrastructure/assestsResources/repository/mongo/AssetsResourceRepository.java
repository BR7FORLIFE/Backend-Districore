package com.tecno_comfenalco.pa.infrastructure.assestsResources.repository.mongo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.assestsResources.entity.AssestsResourceDocument;

public interface AssetsResourceRepository extends MongoRepository<AssestsResourceDocument, UUID> {
    Optional<AssestsResourceDocument> findByIdAndDistributorId(UUID Id, UUID DistributorId);

    Optional<AssestsResourceDocument> findByProductIdAndDistributorId(UUID ProductId, UUID DistributorId);

    boolean existsByProductIdAndDistributorId(UUID ProductId, UUID DistributorId);

    boolean existsByIdAndDistributorId(UUID Id, UUID DistributorId);

    void deleteByIdAndDistributorId(UUID Id, UUID DistributorId);

    Page<AssestsResourceDocument> findByDistributorId(UUID distributorId, Pageable pageable);

    Page<AssestsResourceDocument> findByDistributorIdAndNameContainingIgnoreCase(UUID distributorId, String name,
            Pageable pageable);
}
