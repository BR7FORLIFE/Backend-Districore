package com.tecno_comfenalco.pa.application.store.dto.storeBinding.response;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.BindingStatusEnum;

public record ChangeStatusBindingStoreResponseDto(
                UUID bindingId,
                BindingStatusEnum status,
                String code,
                String message) {

}
