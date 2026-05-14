package com.tecno_comfenalco.pa.infrastructure.presales.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "presales")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PresalesDocument {
    @Id
    private UUID id;
    private UUID distributorId;
    private UUID userId;
    private String name;
    private String phoneNumber;
    private String email;
    private Instant createAt;
    private Instant updateAt;
    private DocumentTypeEnum documentTypeEnum;
    private String documentNumber;
}
