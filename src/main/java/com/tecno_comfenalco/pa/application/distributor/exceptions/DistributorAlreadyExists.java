package com.tecno_comfenalco.pa.application.distributor.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class DistributorAlreadyExists extends GlobalApplicationException {

    public DistributorAlreadyExists() {
        super("Distributor already exists!");
    }
}
