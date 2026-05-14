package com.tecno_comfenalco.pa.domain.distributor.model;

import java.time.Instant;
import java.util.UUID;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

public class DistributorModel {
    private UUID id;
    private UUID userId;
    private String nit;
    private String name;
    private String phoneNumber;
    private String email;
    private Instant createAt;
    private Instant updateAt;
    private DirectionDto directionDto;

    public static DistributorModel createDraft(UUID userId, String nit, String name, String phoneNumber, String email,
            DirectionDto directionDto) {
        return new DistributorModel(UUID.randomUUID(), userId, nit, name, phoneNumber, email, Instant.now(),
                Instant.now(), directionDto);
    }

    public static DistributorModel createNew(UUID id, UUID userId, String nit, String name, String phoneNumber,
            String email, Instant createAt, Instant updateAt,
            DirectionDto directionDto) {
        return new DistributorModel(id, userId, nit, name, phoneNumber, email, createAt, updateAt, directionDto);
    }

    public DistributorModel(UUID id, UUID userId, String nit, String name, String phoneNumber, String email,
            Instant createAt, Instant updateAt, DirectionDto directionDto) {
        this.id = id;
        this.userId = userId;
        this.nit = nit;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.directionDto = directionDto;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getNit() {
        return nit;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public DirectionDto getDirectionDto() {
        return directionDto;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}
