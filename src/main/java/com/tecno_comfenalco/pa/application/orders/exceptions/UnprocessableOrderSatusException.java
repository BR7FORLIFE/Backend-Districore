package com.tecno_comfenalco.pa.application.orders.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class UnprocessableOrderSatusException extends GlobalApplicationException {

    public UnprocessableOrderSatusException() {
        super("status not valid!");
    }
}
