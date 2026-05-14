package com.tecno_comfenalco.pa.application.store.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class StoreBindingAlreadyExistsException extends GlobalApplicationException {

    public StoreBindingAlreadyExistsException() {
        super("store binding already exists!");
    }
}
