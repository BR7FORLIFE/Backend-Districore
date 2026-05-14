package com.tecno_comfenalco.pa.infrastructure.warehouse.entity;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "warehouse")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WareHouseDocument {
    @Id
    private UUID id;
    private UUID distributorId;
    private String name;
    private DirectionDto direction;
    private boolean isActive;
}
