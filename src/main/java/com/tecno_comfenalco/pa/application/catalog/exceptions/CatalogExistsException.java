package com.tecno_comfenalco.pa.application.catalog.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class CatalogExistsException extends GlobalApplicationException {

    public CatalogExistsException() {
        super("Catalog already exists!");
    }
}
