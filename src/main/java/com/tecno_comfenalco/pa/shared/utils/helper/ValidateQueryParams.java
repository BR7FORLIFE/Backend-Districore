package com.tecno_comfenalco.pa.shared.utils.helper;

import com.tecno_comfenalco.pa.application.zGlobalExceptions.QueryParamsExceptions;
import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public class ValidateQueryParams {

    public static void validate(RequestParams params) {
        if (params.page() < 0) {
            throw new QueryParamsExceptions(params.page(), " the current page doesnt not negative!");
        }

        if (params.size() < 0) {
            throw new QueryParamsExceptions(params.size(), "the current size doesnt not negative!");
        }
    }
}
