package com.tecno_comfenalco.pa.application.inventory.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class InventoryAlreadyExistsException extends GlobalApplicationException {

    public InventoryAlreadyExistsException() {
        super("inventory already exists!");
    }
}
