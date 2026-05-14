package com.tecno_comfenalco.pa.application.storeAssignment.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class NoStoresAssignmentException extends GlobalApplicationException {

    public NoStoresAssignmentException() {
        super("Not Stores assignments for the current Distributor!");
    }
}
