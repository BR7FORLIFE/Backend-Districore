package com.tecno_comfenalco.pa.application.delivery.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;
import com.tecno_comfenalco.pa.shared.enums.orders.DeliverStatusOrderEnum;

public class InvalidDeliveryStatusOrderTransitionException extends GlobalApplicationException {

    public InvalidDeliveryStatusOrderTransitionException(DeliverStatusOrderEnum orderStatus) {
        super("Not can be transitioned in the action " + orderStatus + " consider a valid state!");
    }
}
