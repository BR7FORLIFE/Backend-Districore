package com.tecno_comfenalco.pa.application.orders.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class OrderExpirationException extends GlobalApplicationException {

    public OrderExpirationException() {
        super("the current order is expired!");
    }
}
