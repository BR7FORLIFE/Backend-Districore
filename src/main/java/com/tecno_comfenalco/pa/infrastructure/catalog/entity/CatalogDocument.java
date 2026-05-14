package com.tecno_comfenalco.pa.infrastructure.catalog.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tecno_comfenalco.pa.infrastructure.category.entity.CategoryEmbeddedEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "catalog")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogDocument {
    @Id
    private UUID id;
    private UUID distributorId;
    private String catalogCode;
    private String name;
    private Instant createAt;
    private Instant updateAt;
    private List<CategoryEmbeddedEntity> categories;
}
