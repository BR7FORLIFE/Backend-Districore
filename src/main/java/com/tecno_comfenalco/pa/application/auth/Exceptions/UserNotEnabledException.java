package com.tecno_comfenalco.pa.application.auth.Exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class UserNotEnabledException extends GlobalApplicationException {
    
    public UserNotEnabledException(){
        super("User not enabled!");
    }
}
