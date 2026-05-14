package com.tecno_comfenalco.pa.infrastructure.vehicle.entity;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "vehicle")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDocument {

    @Id
    private UUID id;
    private UUID distributorId;
    private String licensePlate;
    private String model;
    private String brand;
}
