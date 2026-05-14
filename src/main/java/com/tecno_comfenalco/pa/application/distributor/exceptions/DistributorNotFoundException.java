package com.tecno_comfenalco.pa.application.distributor.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class DistributorNotFoundException extends GlobalApplicationException {

    public DistributorNotFoundException() {
        super("Distributor not found!");
    }
}
