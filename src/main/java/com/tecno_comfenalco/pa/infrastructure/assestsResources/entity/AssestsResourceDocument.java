package com.tecno_comfenalco.pa.infrastructure.assestsResources.entity;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "assetsResources")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssestsResourceDocument {
    @Id
    private UUID id;
    private UUID distributorId;
    private UUID productId;
    private UUID cloudinaryPublicId;
    private String url;
    private String name;
    private boolean isMain;
    private String format;
    private Double width;
    private Double height;
}
