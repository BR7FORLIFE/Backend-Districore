package com.tecno_comfenalco.pa.application.store.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class StoreAlreadyExistsException extends GlobalApplicationException {

    public StoreAlreadyExistsException(){
        super("store already exists!");
    }
}
