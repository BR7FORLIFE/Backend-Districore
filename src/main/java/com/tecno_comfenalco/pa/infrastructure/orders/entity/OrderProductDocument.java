package com.tecno_comfenalco.pa.infrastructure.orders.entity;

import java.util.UUID;

import com.tecno_comfenalco.pa.shared.enums.Unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductDocument {
    private UUID id;
    private UUID productId;
    private String sku;
    private Long quantity;
    private Double subtotal;
    private Double subtotalIva;
    private Double iva;
    private Double total;
    private Unit unit;
}
