package com.tecno_comfenalco.pa.application.inventory.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class BadInventoryStockException extends GlobalApplicationException {

    public BadInventoryStockException(){
        super("quantity of the inventory is negative!");
    }
}
