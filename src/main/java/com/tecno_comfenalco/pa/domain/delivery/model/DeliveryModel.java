package com.tecno_comfenalco.pa.domain.delivery.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.vehicle.model.VehicleSummaryModel;
import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;
import com.tecno_comfenalco.pa.shared.enums.LicenseTypeEnum;

public class DeliveryModel {
    private UUID id;
    private UUID distributorId;
    private UUID userId;
    private String name;
    private String email;
    private DocumentTypeEnum documentType;
    private String documentNumber;
    private String phoneNumber;
    private String licenseNumber;
    private LicenseTypeEnum licenseType;
    private List<VehicleSummaryModel> vehicles;
    private Instant createAt;
    private Instant updateAt;

    public static DeliveryModel createDraft(UUID distributorId, UUID userId, String name, String email,
            DocumentTypeEnum documentTypeEnum,
            String documentNumber, String phoneNumber, String licenseNumber, LicenseTypeEnum licenseTypeEnum,
            List<VehicleSummaryModel> vehicles) {
        return new DeliveryModel(UUID.randomUUID(), distributorId, userId, name, email, documentTypeEnum,
                documentNumber,
                phoneNumber,
                licenseNumber, licenseTypeEnum, vehicles, Instant.now(), Instant.now());
    }

    public static DeliveryModel createNew(UUID id, UUID distributorId, UUID userId, String name, String email,
            DocumentTypeEnum documentTypeEnum,
            String documentNumber, String phoneNumber, String licenseNumber, LicenseTypeEnum licenseTypeEnum,
            List<VehicleSummaryModel> vehicles, Instant createAt, Instant updateAt) {
        return new DeliveryModel(id, distributorId, userId, name, email, documentTypeEnum, documentNumber, phoneNumber,
                licenseNumber,
                licenseTypeEnum, vehicles, createAt, updateAt);
    }

    public DeliveryModel(UUID id, UUID distributorId, UUID userId, String name, String email,
            DocumentTypeEnum documentType,
            String documentNumber,
            String phoneNumber, String licenseNumber, LicenseTypeEnum licenseType, List<VehicleSummaryModel> vehicles,
            Instant createAt, Instant updateAt) {
        this.id = id;
        this.distributorId = distributorId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
        this.licenseType = licenseType;
        this.vehicles = vehicles;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDistributorId() {
        return distributorId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public DocumentTypeEnum getDocumentType() {
        return documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public LicenseTypeEnum getLicenseType() {
        return licenseType;
    }

    public List<VehicleSummaryModel> getVehicles() {
        return vehicles;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

}
