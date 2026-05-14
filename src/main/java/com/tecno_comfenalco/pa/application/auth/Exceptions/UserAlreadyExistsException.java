package com.tecno_comfenalco.pa.application.auth.Exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class UserAlreadyExistsException extends GlobalApplicationException {

    public UserAlreadyExistsException() {
        super("user already exists!");
    }
}
