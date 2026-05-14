package com.tecno_comfenalco.pa.domain.warehouse.models;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

public class WareHouseModel {
    private UUID id;
    private UUID distributorId;
    private String name;
    private DirectionDto direction;
    private boolean isActive;

    public static WareHouseModel createNew(UUID id, UUID distributorId, String name, DirectionDto directionDto,
            boolean isActive) {
        return new WareHouseModel(id, distributorId, name, directionDto, isActive);
    }

    public static WareHouseModel createDraft(UUID distributorId, String name, DirectionDto directionDto,
            boolean isActive) {
        return new WareHouseModel(UUID.randomUUID(), distributorId, name, directionDto, isActive);
    }

    public WareHouseModel(UUID id, UUID distributorId, String name, DirectionDto directionDto, boolean isActive) {
        this.id = id;
        this.distributorId = distributorId;
        this.name = name;
        this.direction = directionDto;
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDistributorId() {
        return distributorId;
    }

    public String getName() {
        return name;
    }

    public DirectionDto getDirectionDto() {
        return direction;
    }

    public boolean isActive() {
        return isActive;
    }

}
