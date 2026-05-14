package com.tecno_comfenalco.pa.infrastructure.store.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "stores")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDocument {
    @Id
    private UUID id;
    private UUID userId;
    private String name;
    private String nit;
    private String phoneNumber;
    private String email;
    private DirectionDto direction;
    private Instant createAt;
    private Instant updateAt;
}
