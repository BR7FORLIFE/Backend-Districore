package com.tecno_comfenalco.pa.infrastructure.security.services.cookies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.application.auth.dto.requests.LoginRequestDto;
import com.tecno_comfenalco.pa.shared.utils.jwt.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookiesService {

    @Value("${jwt.expiration-ms}")
    private Long expirationMs;

    @Value("${cookie.secure}")
    private boolean isSecure;

    private final JwtService jwtService;

    public CookiesService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void sendCookie(HttpServletResponse response, LoginRequestDto request) {
        long defaultExpiration = 3600000L;
        long configuredExpiration = expirationMs != null ? expirationMs : defaultExpiration;
        long expirationTime = request.rememberMe() ? 7L * 24 * 60 * 60 * 1000 : configuredExpiration;

        String token = jwtService.encode(request.username(), expirationTime);

        Cookie cookie = createCookie("jwt", token, (int) (expirationTime / 1000));
        response.addCookie(cookie);
    }

    public void deleteCookie(HttpServletResponse response) {
        Cookie cookie = createCookie("jwt", "", 0);
        response.addCookie(cookie);
    }

    private Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        cookie.setSecure(isSecure);
        if (isSecure) {
            cookie.setAttribute("SameSite", "None");
        } else {
            cookie.setAttribute("SameSite", "Lax");
        }
        return cookie;
    }
}
