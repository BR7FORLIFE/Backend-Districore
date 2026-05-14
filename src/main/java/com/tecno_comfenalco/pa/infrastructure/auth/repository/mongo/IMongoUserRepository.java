package com.tecno_comfenalco.pa.infrastructure.auth.repository.mongo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.auth.entity.UserDocument;

public interface IMongoUserRepository extends MongoRepository<UserDocument, UUID> {
    Boolean existsByUsername(String username);

    Optional<UserDocument> findByUsername(String username);

    Boolean existsByEmail(String Email);

    Optional<UserDocument> findByEmail(String Email);
}
