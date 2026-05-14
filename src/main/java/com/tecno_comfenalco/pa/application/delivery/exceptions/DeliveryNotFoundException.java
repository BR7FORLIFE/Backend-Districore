package com.tecno_comfenalco.pa.application.delivery.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class DeliveryNotFoundException extends GlobalApplicationException {

    public DeliveryNotFoundException() {
        super("Delivery not Found!");
    }
}
