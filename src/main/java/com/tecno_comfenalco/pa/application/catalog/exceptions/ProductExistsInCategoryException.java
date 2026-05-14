package com.tecno_comfenalco.pa.application.catalog.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class ProductExistsInCategoryException extends GlobalApplicationException {

    public ProductExistsInCategoryException() {
        super("product exists into category!");
    }
}
