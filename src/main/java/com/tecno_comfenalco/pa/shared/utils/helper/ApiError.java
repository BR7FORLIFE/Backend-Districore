package com.tecno_comfenalco.pa.shared.utils.helper;

import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime localDateTime,
        int status,
        String error,
        String message,
        String path) {

}
