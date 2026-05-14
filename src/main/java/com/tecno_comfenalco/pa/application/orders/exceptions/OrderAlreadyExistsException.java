package com.tecno_comfenalco.pa.application.orders.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class OrderAlreadyExistsException extends GlobalApplicationException {

    public OrderAlreadyExistsException() {
        super("order already exists!");
    }
}
