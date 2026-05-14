package com.tecno_comfenalco.pa.application.store.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;
import com.tecno_comfenalco.pa.shared.enums.BindingStatusEnum;

public class InvalidTransitionStateBindingException extends GlobalApplicationException {

    public InvalidTransitionStateBindingException(BindingStatusEnum bindingStatusEnum, String action) {
        super("Cannot " + action + " Binding request status is " + bindingStatusEnum);
    }
}
