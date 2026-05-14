package com.tecno_comfenalco.pa.shared.utils.helper;

import java.util.UUID;

public class SafeParser {
    public static Long safeParseId(String id) {
        try {
            return id != null ? Long.parseLong(id) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static UUID safeParseUUID(String id) {
        try {
            return id != null ? UUID.fromString(id) : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
