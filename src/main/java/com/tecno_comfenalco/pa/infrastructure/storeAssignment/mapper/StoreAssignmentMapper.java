package com.tecno_comfenalco.pa.infrastructure.storeAssignment.mapper;

import com.tecno_comfenalco.pa.domain.storeAssignment.models.StoreAssignmentModel;
import com.tecno_comfenalco.pa.infrastructure.storeAssignment.entity.StoreAssignmentDocument;

public class StoreAssignmentMapper {

    public static StoreAssignmentModel toDomain(StoreAssignmentDocument document) {
        StoreAssignmentModel model = new StoreAssignmentModel(
                document.getId(),
                document.getDistributorId(),
                document.getStoreId(),
                document.getPresales(),
                document.getIsActive(),
                document.getCreateAt(),
                document.getUpdateAt());

        return model;
    }

    public static StoreAssignmentDocument toEntity(StoreAssignmentModel model) {
        StoreAssignmentDocument document = new StoreAssignmentDocument();

        document.setId(model.getId());
        document.setDistributorId(model.getDistributorId());
        document.setStoreId(model.getStoreId());
        document.setPresales(model.getPresales());
        document.setIsActive(model.getIsActive());
        document.setCreateAt(model.getCreateAt());
        document.setUpdateAt(model.getUpdateAt());

        return document;
    }
}
