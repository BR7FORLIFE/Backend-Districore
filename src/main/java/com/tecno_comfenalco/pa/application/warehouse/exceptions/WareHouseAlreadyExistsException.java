package com.tecno_comfenalco.pa.application.warehouse.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class WareHouseAlreadyExistsException extends GlobalApplicationException {

    public WareHouseAlreadyExistsException() {
        super("warehouse already exists!");
    }
}
