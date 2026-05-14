package com.tecno_comfenalco.pa.application.assetsResources.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class AssetsResourceNotFoundException extends GlobalApplicationException {

    public AssetsResourceNotFoundException() {
        super("assets resource not found!");
    }
}
