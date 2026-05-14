package com.tecno_comfenalco.pa.shared.utils.http;

import java.util.List;

public record PagedResult<T>(
        List<T> data,
        PaginationMeta meta) {

}
