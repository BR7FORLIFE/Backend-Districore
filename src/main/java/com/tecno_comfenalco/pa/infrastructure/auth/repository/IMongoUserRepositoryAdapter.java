package com.tecno_comfenalco.pa.infrastructure.auth.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.tecno_comfenalco.pa.application.auth.ports.IUserRepositoryPort;
import com.tecno_comfenalco.pa.domain.auth.models.UserModel;
import com.tecno_comfenalco.pa.infrastructure.auth.entity.UserDocument;
import com.tecno_comfenalco.pa.infrastructure.auth.mapper.UserMapper;
import com.tecno_comfenalco.pa.infrastructure.auth.repository.mongo.IMongoUserRepository;

@Repository
public class IMongoUserRepositoryAdapter implements IUserRepositoryPort {
    private final IMongoUserRepository iMongoUserRepository;

    public IMongoUserRepositoryAdapter(IMongoUserRepository iMongoUserRepository) {
        this.iMongoUserRepository = iMongoUserRepository;
    }

    @Override
    public boolean existsByUsername(String username) {
        return iMongoUserRepository.existsByUsername(username);
    }

    @Override
    public UserModel save(UserModel userModel) {
        UserDocument userDocument = UserMapper.toEntity(userModel);
        UserDocument saved = iMongoUserRepository.save(userDocument);

        return UserMapper.toDomain(saved);
    }

    @Override
    public Optional<UserModel> findByUserId(UUID id) {
        return iMongoUserRepository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public List<UserModel> findAll() {
        return iMongoUserRepository.findAll()
                .stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return iMongoUserRepository.findByUsername(username).map(UserMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return iMongoUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return iMongoUserRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }
}
