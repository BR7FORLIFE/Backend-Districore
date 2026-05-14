package com.tecno_comfenalco.pa.application.product.exceptions;

import java.util.List;

import com.tecno_comfenalco.pa.application.product.exceptions.records.MissingProductInfo;
import com.tecno_comfenalco.pa.application.zGlobalExceptions.GlobalApplicationException;

public class ProductNotExistsByIdsException extends GlobalApplicationException {

    public ProductNotExistsByIdsException(List<MissingProductInfo> obj) {
        super("missing the followings ids: " + obj);
    }
}
