package com.tecno_comfenalco.pa.application.store.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class StoreBindingNotFoundException extends GlobalApplicationException {

    public StoreBindingNotFoundException() {
        super("store binding not found!");
    }
}
