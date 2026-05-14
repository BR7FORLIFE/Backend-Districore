package com.tecno_comfenalco.pa.application.delivery.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class DeliveryAlreadyExistsException extends GlobalApplicationException {

    public DeliveryAlreadyExistsException() {
        super("delivery already exists!");
    }
}
