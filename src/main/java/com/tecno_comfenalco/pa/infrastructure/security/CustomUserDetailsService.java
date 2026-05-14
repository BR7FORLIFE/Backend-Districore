package com.tecno_comfenalco.pa.infrastructure.security;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.application.auth.ports.IUserRepositoryPort;
import com.tecno_comfenalco.pa.domain.auth.models.UserModel;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepositoryPort userRepository;

    public CustomUserDetailsService(IUserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("Authorities: " + new CustomUserDetails(userModel).getAuthorities());
        return new CustomUserDetails(userModel);
    }

    public UserDetails loadUserById(UUID id) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUserId(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(userModel);
    }

    // Registrar nuevo usuario
    public UserDetails registerUser(UserModel userModel) {
        UserModel savedUser = userRepository.save(userModel);
        return new CustomUserDetails(savedUser);
    }

}
