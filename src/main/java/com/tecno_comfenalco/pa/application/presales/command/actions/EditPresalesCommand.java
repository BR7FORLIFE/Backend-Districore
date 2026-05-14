package com.tecno_comfenalco.pa.application.presales.command.actions;

import java.util.UUID;

public record EditPresalesCommand(UUID userDistributorId, UUID presalesId, String name, String phoneNumber) {

}
