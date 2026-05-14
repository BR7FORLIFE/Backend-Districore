package com.tecno_comfenalco.pa.infrastructure.auth.entity;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDocument {

    @Id
    private UUID id;
    private UUID distributorId;
    private String username;
    private String password;
    private String email;
    private boolean enabled;

    private Set<String> roles;
}
