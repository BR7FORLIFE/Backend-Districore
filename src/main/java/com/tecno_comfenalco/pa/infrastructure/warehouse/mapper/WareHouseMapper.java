package com.tecno_comfenalco.pa.infrastructure.warehouse.mapper;

import com.tecno_comfenalco.pa.domain.warehouse.models.WareHouseModel;
import com.tecno_comfenalco.pa.infrastructure.warehouse.entity.WareHouseDocument;

public class WareHouseMapper {

    public static WareHouseModel toDomain(WareHouseDocument wareHouseDocument) {
        WareHouseModel model = WareHouseModel.createNew(
                wareHouseDocument.getId(),
                wareHouseDocument.getDistributorId(),
                wareHouseDocument.getName(),
                wareHouseDocument.getDirection(),
                wareHouseDocument.isActive());

        return model;
    }

    public static WareHouseDocument toEntity(WareHouseModel wareHouseModel) {
        WareHouseDocument document = new WareHouseDocument();

        document.setId(wareHouseModel.getId());
        document.setDistributorId(wareHouseModel.getDistributorId());
        document.setName(wareHouseModel.getName());
        document.setDirection(wareHouseModel.getDirectionDto());
        document.setActive(wareHouseModel.isActive());

        return document;
    }
}
