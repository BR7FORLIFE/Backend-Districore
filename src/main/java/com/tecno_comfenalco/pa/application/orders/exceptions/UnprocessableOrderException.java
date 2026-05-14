package com.tecno_comfenalco.pa.application.orders.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class UnprocessableOrderException extends GlobalApplicationException {

    public UnprocessableOrderException() {
        super("Orders cannot be placed if all products are out of stock!");
    }
}
