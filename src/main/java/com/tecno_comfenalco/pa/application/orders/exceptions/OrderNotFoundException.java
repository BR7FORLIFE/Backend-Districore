package com.tecno_comfenalco.pa.application.orders.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class OrderNotFoundException extends GlobalApplicationException {

    public OrderNotFoundException() {
        super("order not found!");
    }
}
