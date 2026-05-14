package com.tecno_comfenalco.pa.infrastructure.catalog.repository.mongo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.catalog.entity.CatalogDocument;

public interface CatalogRepository extends MongoRepository<CatalogDocument, UUID> {
    boolean existsByDistributorIdAndCatalogCode(UUID distributorId, String catalogCode);

    boolean existsByIdAndCategoriesName(UUID catalogId, String name);

    boolean existsByIdAndCategoriesId(UUID Id, UUID categoryId);

    boolean existsByIdAndCategoriesIdAndCategoriesProductsId(UUID catalogId, UUID categoryId, UUID productId);

    boolean existsByIdAndDistributorId(UUID Id, UUID DistributorId);

    boolean existsById(UUID Id);

    Page<CatalogDocument> findByDistributorIdAndNameContainingIgnoreCase(UUID distributorId, String name,
            Pageable pageable);

    Page<CatalogDocument> findByDistributorId(UUID distributorId, Pageable pageable);

    Optional<CatalogDocument> findByIdAndDistributorId(UUID Id, UUID DistributorId);
}
