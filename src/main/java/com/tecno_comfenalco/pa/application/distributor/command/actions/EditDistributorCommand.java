package com.tecno_comfenalco.pa.application.distributor.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

public record EditDistributorCommand(UUID distributorId, String name, String phoneNumber, DirectionDto direction) {

}
