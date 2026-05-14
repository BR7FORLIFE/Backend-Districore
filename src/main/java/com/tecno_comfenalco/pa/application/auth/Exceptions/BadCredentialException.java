package com.tecno_comfenalco.pa.application.auth.Exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class BadCredentialException extends GlobalApplicationException {

    public BadCredentialException() {
        super("credentials not valid!");
    }
}
