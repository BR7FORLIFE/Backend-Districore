package com.tecno_comfenalco.pa.infrastructure.store.mapper;

import com.tecno_comfenalco.pa.domain.store.models.StoreModel;
import com.tecno_comfenalco.pa.infrastructure.store.entity.StoreDocument;

public class StoreMapper {

    public static StoreModel toDomain(StoreDocument storeDocument) {
        StoreModel store = StoreModel.createNew(storeDocument.getId(), storeDocument.getUserId(),
                storeDocument.getName(), storeDocument.getNit(), storeDocument.getPhoneNumber(),
                storeDocument.getEmail(), storeDocument.getDirection(), storeDocument.getCreateAt(),
                storeDocument.getUpdateAt());

        return store;
    }

    public static StoreDocument toEntity(StoreModel storeModel) {
        StoreDocument store = new StoreDocument();

        store.setId(storeModel.getId());
        store.setUserId(storeModel.getUserId());
        store.setName(storeModel.getName());
        store.setNit(storeModel.getNit());
        store.setPhoneNumber(storeModel.getPhoneNumber());
        store.setEmail(storeModel.getEmail());
        store.setDirection(storeModel.getDirection());
        store.setCreateAt(storeModel.getCreateAt());
        store.setUpdateAt(storeModel.getUpdateAt());

        return store;
    }
}
