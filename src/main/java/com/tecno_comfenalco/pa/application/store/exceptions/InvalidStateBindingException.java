package com.tecno_comfenalco.pa.application.store.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class InvalidStateBindingException extends GlobalApplicationException {

    public InvalidStateBindingException() {
        super("the state doesnt not exists!");
    }
}
