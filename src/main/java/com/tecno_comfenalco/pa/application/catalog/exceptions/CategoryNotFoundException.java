package com.tecno_comfenalco.pa.application.catalog.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class CategoryNotFoundException extends GlobalApplicationException {

    public CategoryNotFoundException() {
        super("Category Not Found!");
    }
}
