package com.tecno_comfenalco.pa.application.auth.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.auth.models.UserModel;

public interface IUserRepositoryPort {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserModel save(UserModel userModel);

    Optional<UserModel> findByUserId(UUID id);

    List<UserModel> findAll();

    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByEmail(String email);
}
