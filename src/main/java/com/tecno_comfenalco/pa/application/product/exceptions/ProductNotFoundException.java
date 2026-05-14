package com.tecno_comfenalco.pa.application.product.exceptions;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class ProductNotFoundException extends GlobalApplicationException {

    public ProductNotFoundException() {
        super("Product not found!");
    }
}
