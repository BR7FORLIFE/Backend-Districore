package com.tecno_comfenalco.pa.application.zGlobalExceptions;

public abstract class GlobalApplicationException extends RuntimeException {
    public GlobalApplicationException(String message) {
        super(message);
    }
}
