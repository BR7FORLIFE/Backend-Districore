package com.tecno_comfenalco.pa.application.zGlobalExceptions;

public class OperationNotAllowedException extends GlobalApplicationException {

    public OperationNotAllowedException() {
        super("operation not allowed or bad process");
    }
}
