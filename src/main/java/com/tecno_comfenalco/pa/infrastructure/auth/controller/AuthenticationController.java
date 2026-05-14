package com.tecno_comfenalco.pa.infrastructure.auth.controller;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.application.auth.command.actions.LoginUserCommand;
import com.tecno_comfenalco.pa.application.auth.command.actions.RegisterUserCommand;
import com.tecno_comfenalco.pa.application.auth.command.response.LoginUserCommandResult;
import com.tecno_comfenalco.pa.application.auth.command.response.RegisterUserCommandResult;
import com.tecno_comfenalco.pa.application.auth.draft.UserDraft;
import com.tecno_comfenalco.pa.application.auth.dto.requests.LoginRequestDto;
import com.tecno_comfenalco.pa.application.auth.dto.requests.RegisterUserRequestDto;
import com.tecno_comfenalco.pa.application.auth.dto.responses.LoginResponseDto;
import com.tecno_comfenalco.pa.application.auth.dto.responses.RegisterUserResponseDto;
import com.tecno_comfenalco.pa.application.auth.usecases.AuthenticationUseCase;
import com.tecno_comfenalco.pa.infrastructure.security.CustomUserDetails;
import com.tecno_comfenalco.pa.infrastructure.security.services.cookies.CookiesService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
@PreAuthorize("permitAll()")
public class AuthenticationController {

    private final AuthenticationUseCase authenticationUseCase;
    private final CookiesService cookiesService;

    public AuthenticationController(AuthenticationUseCase authenticationUseCase, CookiesService cookiesService) {
        this.authenticationUseCase = authenticationUseCase;
        this.cookiesService = cookiesService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> register(@RequestBody @Valid RegisterUserRequestDto dto) {
        RegisterUserCommand cmd = new RegisterUserCommand(dto.username(), dto.password(), dto.email());
        RegisterUserCommandResult result = authenticationUseCase.registerUser(cmd);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterUserResponseDto(result.userId(), result.message()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request, HttpServletResponse response) {
        LoginUserCommand cmd = new LoginUserCommand(request.username(), request.password(), request.rememberMe());
        LoginUserCommandResult result = authenticationUseCase.loginUser(cmd);

        cookiesService.sendCookie(response, request);

        return ResponseEntity
                .ok(new LoginResponseDto("Usuario exitosamente autenticado", result.role(), result.distributorId()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
        cookiesService.deleteCookie(response);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello " + SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/authenticate")
    public ResponseEntity<Map<String, Boolean>> authenticate() {
        return ResponseEntity.ok().body(Map.of("authenticated", true));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<Map<String, UserDraft>> me(Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        Set<String> roles = details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        UserDraft user = new UserDraft(details.getUserId(), details.getUsername(), roles,
                details.isEnabled());
        return ResponseEntity.ok().body(Map.of("user", user));
    }
}
