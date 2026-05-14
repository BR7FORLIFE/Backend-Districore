package com.tecno_comfenalco.pa.infrastructure.presales.mapper;

import com.tecno_comfenalco.pa.domain.presales.model.PresalesModel;
import com.tecno_comfenalco.pa.infrastructure.presales.entity.PresalesDocument;

public class PresalesMapper {

    public static PresalesModel toDomain(PresalesDocument presalesDocument) {
        PresalesModel presalesModel = PresalesModel.createNew(presalesDocument.getId(),
                presalesDocument.getDistributorId(), presalesDocument.getUserId(), presalesDocument.getName(),
                presalesDocument.getPhoneNumber(),
                presalesDocument.getEmail(), presalesDocument.getCreateAt(), presalesDocument.getUpdateAt(),
                presalesDocument.getDocumentTypeEnum(),
                presalesDocument.getDocumentNumber());

        return presalesModel;
    }

    public static PresalesDocument toEntity(PresalesModel presalesModel) {
        PresalesDocument presalesDocument = new PresalesDocument();
        presalesDocument.setId(presalesModel.getId());
        presalesDocument.setDistributorId(presalesModel.getDistributorId());
        presalesDocument.setUserId(presalesModel.getUserId());
        presalesDocument.setName(presalesModel.getName());
        presalesDocument.setPhoneNumber(presalesModel.getPhoneNumber());
        presalesDocument.setEmail(presalesModel.getEmail());
        presalesDocument.setCreateAt(presalesModel.getCreateAt());
        presalesDocument.setUpdateAt(presalesModel.getUpdateAt());
        presalesDocument.setDocumentTypeEnum(presalesModel.getDocumentTypeEnum());
        presalesDocument.setDocumentNumber(presalesModel.getDocumentNumber());

        return presalesDocument;
    }
}
