package com.tecno_comfenalco.pa.application.orders.draft;

import java.util.UUID;

public record OrderProductDraft(
                UUID productId,
                Long quantity) {
}
