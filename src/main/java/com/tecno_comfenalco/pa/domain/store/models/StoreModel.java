package com.tecno_comfenalco.pa.domain.store.models;

import java.time.Instant;
import java.util.UUID;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

public class StoreModel {
    private UUID id;
    private UUID userId;
    private String name;
    private String nit;
    private String phoneNumber;
    private String email;
    private DirectionDto direction;
    private Instant createAt;
    private Instant updateAt;

    public static StoreModel createNew(UUID id, UUID userId, String name, String nit, String phoneNumber, String email,
            DirectionDto directionDto, Instant createAt, Instant updateAt) {
        return new StoreModel(id, userId, name, nit, phoneNumber, email, directionDto, createAt, updateAt);
    }

    public static StoreModel createDraft(UUID userId, String name, String nit, String phoneNumber, String email,
            DirectionDto directionDto, Instant createAt, Instant updateAt) {
        return new StoreModel(UUID.randomUUID(), userId, name, nit, phoneNumber, email, directionDto, createAt,
                updateAt);
    }

    public StoreModel(UUID id, UUID userId, String name, String nit, String phoneNumber, String email,
            DirectionDto directionDto, Instant createAt, Instant updateAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.nit = nit;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.direction = directionDto;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getNit() {
        return nit;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public DirectionDto getDirection() {
        return direction;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

}
