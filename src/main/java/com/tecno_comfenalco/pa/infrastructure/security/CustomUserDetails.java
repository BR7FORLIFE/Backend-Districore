package com.tecno_comfenalco.pa.infrastructure.security;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tecno_comfenalco.pa.domain.auth.models.UserModel;

public class CustomUserDetails implements UserDetails {
    private UserModel userModel;

    public CustomUserDetails(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userModel.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userModel.getPassword();
    }

    @Override
    public String getUsername() {
        return userModel.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return userModel.isEnabled();
    }

    public UUID getUserId() {
        return userModel.getId();
    }

    public UUID getDistributorId() {
        return userModel.getDistributorId();
    }
}
