package com.tecno_comfenalco.pa.infrastructure.store.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.tecno_comfenalco.pa.shared.enums.BindingStatusEnum;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "binding-request")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreBindingRequestDocument {
    @Id
    private UUID id;
    private UUID presalesId;
    private UUID distributorId;
    private String nit;
    private String tempName;
    private BindingStatusEnum bindingStatus;
    private boolean isConsumed;
    private String activationCode;
    private Instant createAt;
    private Instant consumedAt;
}
