package com.tecno_comfenalco.pa.application.distributor.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

public record RegisterDistributorCommand(UUID userId, String nit, String name, String phoneNumber, String email,
                DirectionDto directionDto) {

}
