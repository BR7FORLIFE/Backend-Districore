package com.tecno_comfenalco.pa.application.orders.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;
import com.tecno_comfenalco.pa.shared.enums.orders.RequestStatusOrderEnum;

public class InvalidOrderRequestTransitionException extends GlobalApplicationException {

    public InvalidOrderRequestTransitionException(RequestStatusOrderEnum statusOrder) {
        super("Not can be transitioned. When the status order is " + statusOrder
                + " Consider transitioned a valid state!");
    }
}
