package com.tecno_comfenalco.pa.application.inventory.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class InventoryNotFoundException extends GlobalApplicationException {

    public InventoryNotFoundException() {
        super("inventory not found!");
    }
}
