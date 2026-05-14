package com.tecno_comfenalco.pa.infrastructure.inventory.entity;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDocument {
    @Id
    private UUID id;
    private UUID distributorId;
    private UUID productId;
    private UUID warehouseId;
    private Long quantity;
}
