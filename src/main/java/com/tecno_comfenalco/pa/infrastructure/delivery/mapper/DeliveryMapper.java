package com.tecno_comfenalco.pa.infrastructure.delivery.mapper;

import java.util.List;

import com.tecno_comfenalco.pa.domain.delivery.model.DeliveryModel;
import com.tecno_comfenalco.pa.domain.vehicle.model.VehicleSummaryModel;
import com.tecno_comfenalco.pa.infrastructure.delivery.entity.DeliveryDocument;
import com.tecno_comfenalco.pa.infrastructure.vehicle.entity.VehicleSummaryDocument;
import com.tecno_comfenalco.pa.infrastructure.vehicle.mapper.VehicleMapper;

public class DeliveryMapper {

    public static DeliveryModel toDomain(DeliveryDocument deliveryDocument) {

        List<VehicleSummaryModel> vehiclesModel = (deliveryDocument.getVehicles() == null)
                ? List.of()
                : deliveryDocument.getVehicles()
                        .stream()
                        .map(VehicleMapper::toSummaryModel)
                        .toList();

        return DeliveryModel.createNew(
                deliveryDocument.getId(),
                deliveryDocument.getDistributorId(),
                deliveryDocument.getUserId(),
                deliveryDocument.getName(),
                deliveryDocument.getEmail(),
                deliveryDocument.getDocumentType(),
                deliveryDocument.getDocumentNumber(),
                deliveryDocument.getPhoneNumber(),
                deliveryDocument.getLicenseNumber(),
                deliveryDocument.getLicenseType(),
                vehiclesModel,
                deliveryDocument.getCreateAt(),
                deliveryDocument.getUpdateAt());
    }

    public static DeliveryDocument toEntity(DeliveryModel deliveryModel) {

        List<VehicleSummaryDocument> vehicles = (deliveryModel.getVehicles() == null)
                ? List.of()
                : deliveryModel.getVehicles()
                        .stream()
                        .map(VehicleMapper::toSummaryEntity)
                        .toList();

        DeliveryDocument delivery = new DeliveryDocument();

        delivery.setId(deliveryModel.getId());
        delivery.setDistributorId(deliveryModel.getDistributorId());
        delivery.setUserId(deliveryModel.getUserId());
        delivery.setName(deliveryModel.getName());
        delivery.setEmail(deliveryModel.getEmail());
        delivery.setDocumentType(deliveryModel.getDocumentType());
        delivery.setDocumentNumber(deliveryModel.getDocumentNumber());
        delivery.setPhoneNumber(deliveryModel.getPhoneNumber());
        delivery.setLicenseNumber(deliveryModel.getLicenseNumber());
        delivery.setLicenseType(deliveryModel.getLicenseType());
        delivery.setVehicles(vehicles);
        delivery.setCreateAt(deliveryModel.getCreateAt());
        delivery.setUpdateAt(deliveryModel.getUpdateAt());

        return delivery;
    }
}
