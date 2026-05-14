package com.tecno_comfenalco.pa.domain.assestsResources.model;

import java.util.UUID;

public class AssestsResourceModel {
    private UUID id;
    private UUID distributorId;
    private UUID productId;
    private UUID cloudinaryPublicId;
    private String url;
    private String name;
    private boolean isMain;
    private String format;
    private Double width;
    private Double height;

    public static AssestsResourceModel createNew(UUID id, UUID distributorId, UUID productId, UUID cloudinaryPublicId,
            String url, String name, boolean isMain, String format, Double width, Double height) {
        return new AssestsResourceModel(id, distributorId, productId, cloudinaryPublicId, url, name, isMain, format,
                width, height);
    }

    public static AssestsResourceModel createDraft(UUID distributorId, UUID productId, UUID cloudinaryPublicId,
            String url, String name, boolean isMain, String format, Double width, Double height) {
        return new AssestsResourceModel(UUID.randomUUID(), distributorId, productId, cloudinaryPublicId, url, name,
                isMain, format,
                width, height);
    }

    public AssestsResourceModel(UUID id, UUID distributorId, UUID productId, UUID cloudinaryPublicId, String url,
            String name, boolean isMain, String format, Double width, Double height) {
        this.id = id;
        this.distributorId = distributorId;
        this.productId = productId;
        this.cloudinaryPublicId = cloudinaryPublicId;
        this.url = url;
        this.name = name;
        this.isMain = isMain;
        this.format = format;
        this.width = width;
        this.height = height;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDistributorId() {
        return distributorId;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getCloudinaryPublicId() {
        return cloudinaryPublicId;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public boolean isMain() {
        return isMain;
    }

    public String getFormat() {
        return format;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

}
