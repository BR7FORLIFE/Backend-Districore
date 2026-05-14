package com.tecno_comfenalco.pa.infrastructure.assestsResources.mapper;

import com.tecno_comfenalco.pa.domain.assestsResources.model.AssestsResourceModel;
import com.tecno_comfenalco.pa.infrastructure.assestsResources.entity.AssestsResourceDocument;

public class AssestsResourceMapper {

    public static AssestsResourceModel toDomain(AssestsResourceDocument document) {
        AssestsResourceModel model = AssestsResourceModel.createNew(
                document.getId(),
                document.getDistributorId(),
                document.getProductId(),
                document.getCloudinaryPublicId(),
                document.getUrl(),
                document.getName(),
                document.isMain(),
                document.getFormat(),
                document.getWidth(),
                document.getHeight());

        return model;
    }

    public static AssestsResourceDocument toEntity(AssestsResourceModel model) {
        AssestsResourceDocument document = new AssestsResourceDocument();

        document.setId(model.getId());
        document.setDistributorId(model.getDistributorId());
        document.setProductId(model.getProductId());
        document.setCloudinaryPublicId(model.getCloudinaryPublicId());
        document.setUrl(model.getUrl());
        document.setName(model.getName());
        document.setMain(model.isMain());
        document.setFormat(model.getFormat());
        document.setWidth(model.getWidth());
        document.setHeight(model.getHeight());

        return document;
    }
}
