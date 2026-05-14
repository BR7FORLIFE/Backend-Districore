package com.tecno_comfenalco.pa.infrastructure.store.mapper;

import com.tecno_comfenalco.pa.domain.store.models.StoreBindingRequestModel;
import com.tecno_comfenalco.pa.infrastructure.store.entity.StoreBindingRequestDocument;

public class StoreBindingMapper {

    public static StoreBindingRequestModel toDomain(StoreBindingRequestDocument entity) {
        StoreBindingRequestModel model = StoreBindingRequestModel.createNew(
                entity.getId(),
                entity.getPresalesId(),
                entity.getDistributorId(),
                entity.getNit(),
                entity.getTempName(),
                entity.getBindingStatus(),
                entity.isConsumed(),
                entity.getActivationCode(),
                entity.getCreateAt(),
                entity.getConsumedAt());

        return model;
    }

    public static StoreBindingRequestDocument toEntity(StoreBindingRequestModel model) {
        StoreBindingRequestDocument storeBinding = new StoreBindingRequestDocument();

        storeBinding.setId(model.getId());
        storeBinding.setPresalesId(model.getPresalesId());
        storeBinding.setDistributorId(model.getDistributorId());
        storeBinding.setNit(model.getNit());
        storeBinding.setTempName(model.getTempName());
        storeBinding.setBindingStatus(model.getBindingStatus());
        storeBinding.setConsumed(model.isConsumed());
        storeBinding.setActivationCode(model.getActivationCode());
        storeBinding.setCreateAt(model.getCreateAt());
        storeBinding.setConsumedAt(model.getConsumedAt());

        return storeBinding;
    }

}
