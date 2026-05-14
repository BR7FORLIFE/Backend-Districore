package com.tecno_comfenalco.pa.application.presales.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class PresalesAlreadyExistsException extends GlobalApplicationException {

    public PresalesAlreadyExistsException() {
        super("Presales Already exists!");
    }
}
