package com.tecno_comfenalco.pa.domain.vehicle.model;

import java.util.UUID;

public class VehicleSummaryModel {
    private UUID id;
    private UUID distributorId;
    private String licensePlate;
    private String model;
    private String brand;

    public static VehicleSummaryModel createDraft(UUID distributorId, String licensePlate, String model, String brand) {
        return new VehicleSummaryModel(UUID.randomUUID(), distributorId, licensePlate, model, brand);
    }

    public static VehicleSummaryModel createNew(UUID id, UUID distributorId, String licensePlate, String model,
            String brand) {
        return new VehicleSummaryModel(id, distributorId, licensePlate, model, brand);
    }

    public VehicleSummaryModel(UUID id, UUID distributorId, String licensePlate, String model, String brand) {
        this.id = id;
        this.distributorId = distributorId;
        this.licensePlate = licensePlate;
        this.model = model;
        this.brand = brand;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDistributorId() {
        return distributorId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

}
