package com.tecno_comfenalco.pa.application.auth.Exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class AuthenticationException extends GlobalApplicationException {

    public AuthenticationException() {
        super("Authentication failed!");
    }
}
