package com.tecno_comfenalco.pa.domain.presales.model;

import java.time.Instant;
import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;

public class PresalesModel {
    private UUID id;
    private UUID distributorId;
    private UUID userId;
    private String name;
    private String phoneNumber;
    private String email;
    private Instant createAt;
    private Instant updateAt;
    private DocumentTypeEnum documentTypeEnum;
    private String documentNumber;

    public static PresalesModel createNew(UUID id, UUID distributorId, UUID userId, String name, String phoneNumber,
            String email,
            Instant createAt, Instant updateAt,
            DocumentTypeEnum documentTypeEnum, String documentNumber) {
        return new PresalesModel(id, distributorId, userId, name, phoneNumber, email, createAt, updateAt,
                documentTypeEnum,
                documentNumber);
    }

    public static PresalesModel createDraft(UUID distributorId, UUID userId, String name, String phoneNumber,
            String email,
            DocumentTypeEnum documentTypeEnum, String documentNumber) {
        return new PresalesModel(UUID.randomUUID(), distributorId, userId, name, phoneNumber, email, Instant.now(),
                Instant.now(), documentTypeEnum,
                documentNumber);
    }

    public PresalesModel(UUID id, UUID distributorId, UUID userId, String name, String phoneNumber, String email,
            Instant createAt,
            Instant updateAt,
            DocumentTypeEnum documentTypeEnum, String documentNumber) {
        this.id = id;
        this.distributorId = distributorId;
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.documentTypeEnum = documentTypeEnum;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.documentNumber = documentNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public DocumentTypeEnum getDocumentTypeEnum() {
        return documentTypeEnum;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

}
