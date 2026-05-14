package com.tecno_comfenalco.pa.application.presales.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class PresalesNotFoundException extends GlobalApplicationException {

    public PresalesNotFoundException() {
        super("Presales Not found!");
    }
}
