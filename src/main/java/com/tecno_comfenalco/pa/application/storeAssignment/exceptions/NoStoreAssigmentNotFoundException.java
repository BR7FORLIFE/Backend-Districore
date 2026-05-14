package com.tecno_comfenalco.pa.application.storeAssignment.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class NoStoreAssigmentNotFoundException extends GlobalApplicationException {

    public NoStoreAssigmentNotFoundException() {
        super("store assignment not found!");
    }
}
