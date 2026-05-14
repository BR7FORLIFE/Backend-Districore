package com.tecno_comfenalco.pa.infrastructure.product.repository.mongo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.product.entity.ProductDocument;

public interface ProductRepository extends MongoRepository<ProductDocument, UUID> {
    boolean existsByDistributorIdAndSku(UUID distributorId, String sku);

    boolean existsByIdAndDistributorId(UUID id, UUID distributorId);

    void deleteByIdAndDistributorId(UUID productId, UUID distributorId);

    Page<ProductDocument> findByDistributorId(UUID distributorId, Pageable pageable);

    Page<ProductDocument> findByDistributorIdAndNameContainingIgnoreCase(UUID distributorId, String name,
            Pageable pageable);

    Optional<ProductDocument> findByDistributorIdAndId(UUID distributorId, UUID id);

    List<ProductDocument> findByDistributorIdAndIdIn(UUID DistributorId, List<UUID> ids);
}
