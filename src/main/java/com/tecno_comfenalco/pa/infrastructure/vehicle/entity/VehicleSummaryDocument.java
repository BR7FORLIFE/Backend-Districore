package com.tecno_comfenalco.pa.infrastructure.vehicle.entity;

import java.util.UUID;

public class VehicleSummaryDocument {
    private UUID id;
    private UUID distributorId;
    private String licensePlate;
    private String model;
    private String brand;

    public VehicleSummaryDocument() {
    }

    public VehicleSummaryDocument(UUID id, UUID distributorId, String licensePlate, String model, String brand) {
        this.id = id;
        this.distributorId = distributorId;
        this.licensePlate = licensePlate;
        this.model = model;
        this.brand = brand;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBrand(String brand) {
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
