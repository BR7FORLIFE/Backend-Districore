package com.tecno_comfenalco.pa.application.store.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class StoreNotFoundException extends GlobalApplicationException {

    public StoreNotFoundException() {
        super("store not found!");
    }
}
