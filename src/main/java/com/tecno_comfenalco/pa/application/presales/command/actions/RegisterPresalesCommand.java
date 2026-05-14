package com.tecno_comfenalco.pa.application.presales.command.actions;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;

public record RegisterPresalesCommand(UUID userDistributorId, String username, String password, String name,
        String phoneNumber, String email,
        DocumentTypeEnum documentType,
        String documentNumber) {

}
