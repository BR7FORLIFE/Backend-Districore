package com.tecno_comfenalco.pa.application.catalog.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class CategoryExistsException extends GlobalApplicationException {

    public CategoryExistsException() {
        super("Category already exists!");
    }
}
