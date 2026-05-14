package com.tecno_comfenalco.pa.application.assetsResources.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class AssetsResourceAlreadyExistsException extends GlobalApplicationException {

    public AssetsResourceAlreadyExistsException() {
        super("assets resource already exists!");
    }
}
