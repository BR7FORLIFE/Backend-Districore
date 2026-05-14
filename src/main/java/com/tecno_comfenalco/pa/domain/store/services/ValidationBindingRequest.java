package com.tecno_comfenalco.pa.domain.store.services;

import java.time.Instant;

import com.tecno_comfenalco.pa.application.store.exceptions.InvalidStateBindingException;
import com.tecno_comfenalco.pa.application.store.exceptions.InvalidTransitionStateBindingException;
import com.tecno_comfenalco.pa.domain.store.models.StoreBindingRequestModel;
import com.tecno_comfenalco.pa.shared.enums.BindingStatusEnum;

public class ValidationBindingRequest {

    public static StoreBindingRequestModel validateTransitionState(
            StoreBindingRequestModel currentBinding,
            BindingStatusEnum statusToTransitioned,
            String code,
            boolean isDistributor) {

        if (isDistributor && statusToTransitioned == BindingStatusEnum.ACTIVE) {
            throw new InvalidTransitionStateBindingException(statusToTransitioned,
                    "activate the binding because this action is restricted to the Store.");
        }

        StoreBindingRequestModel updateBinding;

        switch (statusToTransitioned) {
            case APPROVED:
                // si ocurre alguna exception no se puede transicionar el estado
                currentBinding.getBindingStatus().approve();
                updateBinding = createNewBinding(currentBinding, statusToTransitioned, code, false);
                break;

            case REJECTED:
                currentBinding.getBindingStatus().rejected();
                updateBinding = createNewBinding(currentBinding, statusToTransitioned, code, false);
                break;

            case ACTIVE:
                currentBinding.getBindingStatus().active();
                updateBinding = createNewBinding(currentBinding, statusToTransitioned, code, true);
                break;

            case PENDING:
                throw new InvalidTransitionStateBindingException(statusToTransitioned, "pending");

            default:
                throw new InvalidStateBindingException();
        }

        return updateBinding;
    }

    private static StoreBindingRequestModel createNewBinding(StoreBindingRequestModel model, BindingStatusEnum status,
            String code,
            boolean isConsumed) {
        return StoreBindingRequestModel.createNew(
                model.getId(),
                model.getPresalesId(),
                model.getDistributorId(),
                model.getNit(),
                model.getTempName(),
                status,
                isConsumed,
                code == null ? model.getActivationCode() : code,
                model.getCreateAt(),
                isConsumed ? Instant.now() : model.getConsumedAt());
    }
}
