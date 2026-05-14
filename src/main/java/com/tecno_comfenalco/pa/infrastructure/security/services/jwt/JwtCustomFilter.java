package com.tecno_comfenalco.pa.infrastructure.security.services.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tecno_comfenalco.pa.infrastructure.security.CustomUserDetailsService;
import com.tecno_comfenalco.pa.shared.utils.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtCustomFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        String username = null;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 💡 Lógica para decodificar y validar el token
        if (token != null) {
            try {
                username = jwtUtils.decode(token);
            } catch (Exception e) {
                System.out.println("Invalid JWT from cookie: " + e.getMessage());
                Cookie invalidCookie = new Cookie("jwt", "");
                invalidCookie.setHttpOnly(true);
                invalidCookie.setSecure(false);
                invalidCookie.setPath("/");
                invalidCookie.setMaxAge(0);
                invalidCookie.setAttribute("SameSite", "None");
                response.addCookie(invalidCookie);
                token = null;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtils.validate(token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                System.out.println("Error loading user from JWT: " + e.getMessage());
                Cookie invalidCookie = new Cookie("jwt", "");
                invalidCookie.setHttpOnly(true);
                invalidCookie.setSecure(false);
                invalidCookie.setPath("/");
                invalidCookie.setMaxAge(0);
                invalidCookie.setAttribute("SameSite", "None");
                response.addCookie(invalidCookie);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.contains("/v3/api-docs") ||
                path.contains("/swagger-ui") ||
                path.contains("/swagger-resources");
    }
}
