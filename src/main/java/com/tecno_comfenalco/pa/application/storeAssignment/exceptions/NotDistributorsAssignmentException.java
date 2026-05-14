package com.tecno_comfenalco.pa.application.storeAssignment.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class NotDistributorsAssignmentException extends GlobalApplicationException {

    public NotDistributorsAssignmentException() {
        super("Not distributors assignments for the current store!");
    }
}
