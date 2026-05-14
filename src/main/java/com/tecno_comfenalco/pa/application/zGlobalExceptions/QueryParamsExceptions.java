package com.tecno_comfenalco.pa.application.zGlobalExceptions;

public class QueryParamsExceptions extends GlobalApplicationException {

    public QueryParamsExceptions(Object param, String intention) {
        super("error in the query params: " + param + " " + intention);
    }
}
