package com.tecno_comfenalco.pa.application.delivery.command.actions;

import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.vehicle.model.VehicleSummaryModel;
import com.tecno_comfenalco.pa.shared.enums.LicenseTypeEnum;

public record UpdateDeliveryCommand(UUID userDistributorId, UUID deliveryId, String name, String phoneNumber,
        LicenseTypeEnum licenseTypeEnum, List<VehicleSummaryModel> vehicles) {

}
