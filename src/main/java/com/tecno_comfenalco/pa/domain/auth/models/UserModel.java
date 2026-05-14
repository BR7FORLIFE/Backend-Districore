package com.tecno_comfenalco.pa.domain.auth.models;

import java.util.Set;
import java.util.UUID;

public class UserModel {
    private UUID id;
    private UUID distributorId;
    private String username;
    private String password;
    private boolean enabled;
    private String email;
    private Set<String> roles;

    public UserModel() {
    }

    public static UserModel createNew(UUID id, UUID distributorId, String username, String password, Set<String> roles,
            String email,
            boolean enabled) {
        return UserModel.builder()
                .id(id)
                .distributorId(distributorId)
                .username(username)
                .password(password)
                .email(email)
                .roles(roles)
                .enabled(enabled)
                .build();
    }

    public static UserModel createDraft(UUID distributorId, String username, String password, Set<String> roles,
            String email,
            boolean enabled) {
        return UserModel.builder()
                .id(UUID.randomUUID())
                .distributorId(distributorId)
                .username(username)
                .password(password)
                .email(email)
                .roles(roles)
                .enabled(enabled)
                .build();
    }

    private UserModel(Builder builder) {
        this.id = builder.id;
        this.distributorId = builder.distributorId;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.enabled = builder.enabled;
        this.roles = builder.roles;
    }

    public static UserModel ChangeEnabled(UserModel userModel, boolean enabled) {
        return UserModel.builder()
                .id(userModel.getId())
                .username(userModel.getUsername())
                .password(userModel.getPassword())
                .roles(userModel.getRoles())
                .enabled(enabled)
                .build();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getEmail() {
        return email;
    }

    public UUID getDistributorId() {
        return distributorId;
    }

    public static class Builder {
        private UUID id;
        private UUID distributorId;
        private String username;
        private String password;
        private boolean enabled;
        private String email;
        private Set<String> roles;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder distributorId(UUID distributorId) {
            this.distributorId = distributorId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder roles(Set<String> roles) {
            this.roles = roles;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserModel build() {
            return new UserModel(this);
        }
    }
}
