package com.tecno_comfenalco.pa.infrastructure.distributor.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "distributor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistributorDocument {
    @Id
    private UUID id;
    private UUID userId;
    private String nit;
    private String name;
    private String phoneNumber;
    private String email;
    private Instant createAt;
    private Instant updateAt;
    private DirectionDto directionDto;
}
