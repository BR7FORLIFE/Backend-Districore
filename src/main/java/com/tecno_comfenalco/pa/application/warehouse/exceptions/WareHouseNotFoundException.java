package com.tecno_comfenalco.pa.application.warehouse.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class WareHouseNotFoundException extends GlobalApplicationException {

    public WareHouseNotFoundException() {
        super("warehouse not found!");
    }
}
