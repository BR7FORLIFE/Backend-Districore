package com.tecno_comfenalco.pa.application.catalog.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class CatalogNotFoundException extends GlobalApplicationException {

    public CatalogNotFoundException() {
        super("Catalog Not found!");
    }
}
