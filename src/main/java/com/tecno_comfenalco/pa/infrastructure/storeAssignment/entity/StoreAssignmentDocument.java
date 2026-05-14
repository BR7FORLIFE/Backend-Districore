package com.tecno_comfenalco.pa.infrastructure.storeAssignment.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.tecno_comfenalco.pa.application.presales.draft.PresalesDraft;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "store-assignment")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StoreAssignmentDocument {
    @Id
    private UUID id;
    private UUID distributorId;
    private UUID storeId;
    private List<PresalesDraft> presales;
    private Boolean isActive;
    private Instant createAt;
    private Instant updateAt;
}
