package com.tecno_comfenalco.pa.infrastructure.product.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tecno_comfenalco.pa.shared.enums.Unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDocument {
    @Id
    private UUID id;
    private UUID distributorId;
    private String sku;
    private String name;
    private Unit unit;
    private Double price;
    private Instant createAt;
    private Instant updateAt;
}
