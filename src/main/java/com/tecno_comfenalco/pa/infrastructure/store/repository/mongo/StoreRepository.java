package com.tecno_comfenalco.pa.infrastructure.store.repository.mongo;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.store.entity.StoreDocument;

public interface StoreRepository extends MongoRepository<StoreDocument, UUID> {

    boolean existsById(UUID Id);

    boolean existsByNit(String Nit);

    Page<StoreDocument> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<StoreDocument> findById(UUID Id);

    void deleteById(UUID Id);

    Optional<StoreDocument> findByUserId(UUID UserId);

    Page<StoreDocument> findByIdInAndNameContainingIgnoreCase(
            Collection<UUID> ids,
            String name,
            Pageable pageable);

    Page<StoreDocument> findByIdIn(Collection<UUID> ids, Pageable pageable);
}
