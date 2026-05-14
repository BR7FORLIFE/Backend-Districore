package com.tecno_comfenalco.pa.domain.storeAssignment.models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.application.presales.draft.PresalesDraft;

public class StoreAssignmentModel {
    private UUID id;
    private UUID distributorId;
    private UUID storeId;
    private List<PresalesDraft> presales;
    private Boolean isActive;
    private Instant createAt;
    private Instant updateAt;

    public static StoreAssignmentModel createNew(UUID id, UUID distributorId, UUID storeId,
            List<PresalesDraft> presales,
            Boolean isActive, Instant createAt, Instant updateAt) {
        return new StoreAssignmentModel(id, distributorId, storeId, presales, isActive, createAt, updateAt);
    }

    public static StoreAssignmentModel createDraft(
            UUID distributorId,
            UUID storeId,
            List<PresalesDraft> presales,
            Boolean isActive) {

        return new StoreAssignmentModel(
                UUID.randomUUID(),
                distributorId,
                storeId,
                presales,
                isActive,
                Instant.now(),
                Instant.now());
    }

    public StoreAssignmentModel(UUID id, UUID distributorId, UUID storeId, List<PresalesDraft> presales,
            Boolean isActive, Instant createAt, Instant updateAt) {
        this.id = id;
        this.distributorId = distributorId;
        this.storeId = storeId;
        this.presales = presales;
        this.isActive = isActive;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDistributorId() {
        return distributorId;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public List<PresalesDraft> getPresales() {
        return presales;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

}
