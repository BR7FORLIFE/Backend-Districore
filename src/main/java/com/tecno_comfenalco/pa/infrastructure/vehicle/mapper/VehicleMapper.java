package com.tecno_comfenalco.pa.infrastructure.vehicle.mapper;

import com.tecno_comfenalco.pa.domain.vehicle.model.VehicleModel;
import com.tecno_comfenalco.pa.domain.vehicle.model.VehicleSummaryModel;
import com.tecno_comfenalco.pa.infrastructure.vehicle.entity.VehicleDocument;
import com.tecno_comfenalco.pa.infrastructure.vehicle.entity.VehicleSummaryDocument;

public class VehicleMapper {

    public static VehicleModel toDomain(VehicleDocument vehicleDocument) {
        VehicleModel vehicle = VehicleModel.createNew(vehicleDocument.getId(), vehicleDocument.getDistributorId(),
                vehicleDocument.getLicensePlate(), vehicleDocument.getModel(), vehicleDocument.getBrand());

        return vehicle;
    }

    public static VehicleDocument toEntity(VehicleModel vehicleModel) {
        VehicleDocument vehicle = new VehicleDocument();

        vehicle.setId(vehicleModel.getId());
        vehicle.setDistributorId(vehicleModel.getDistributorId());
        vehicle.setLicensePlate(vehicleModel.getLicensePlate());
        vehicle.setModel(vehicleModel.getModel());
        vehicle.setBrand(vehicleModel.getBrand());

        return vehicle;
    }

    public static VehicleSummaryModel toSummaryModel(VehicleSummaryDocument vehicleSummaryDocument) {
        if (vehicleSummaryDocument != null) {
            VehicleSummaryModel vehicleSummary = VehicleSummaryModel.createNew(vehicleSummaryDocument.getId(),
                    vehicleSummaryDocument.getDistributorId(), vehicleSummaryDocument.getLicensePlate(),
                    vehicleSummaryDocument.getModel(), vehicleSummaryDocument.getBrand());

            return vehicleSummary;
        }

        return null;
    }

    public static VehicleSummaryDocument toSummaryEntity(VehicleSummaryModel vehicleSummaryModel) {
        if (vehicleSummaryModel != null) {
            VehicleSummaryDocument vehicleSummary = new VehicleSummaryDocument();

            vehicleSummary.setId(vehicleSummaryModel.getId());
            vehicleSummary.setDistributorId(vehicleSummaryModel.getDistributorId());
            vehicleSummary.setLicensePlate(vehicleSummaryModel.getLicensePlate());
            vehicleSummary.setModel(vehicleSummaryModel.getModel());
            vehicleSummary.setBrand(vehicleSummaryModel.getBrand());

            return vehicleSummary;
        }

        return null;
    }
}
