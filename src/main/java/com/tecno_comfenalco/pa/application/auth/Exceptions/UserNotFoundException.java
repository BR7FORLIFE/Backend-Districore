package com.tecno_comfenalco.pa.application.auth.Exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class UserNotFoundException extends GlobalApplicationException {
    
    public UserNotFoundException(){
        super("user not found!");
    }
}
