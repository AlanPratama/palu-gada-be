package com.palu_gada_be.palu_gada_be.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AdminSecretKeyFilter extends OncePerRequestFilter {
    private static final String ADMIN_EXPECTED_SECRET_KEY = "zul customer alan login tomy semut hadiat autentikasi";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String adminKey = request.getHeader("X-Admin-Secret-Key");

        if (path.startsWith("/api/v1/auth/create-admin")) {
            if (adminKey == null || !adminKey.equals(ADMIN_EXPECTED_SECRET_KEY)) {
                response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid Admin Secret Key");
                return;
            }
        }

        // Lanjutkan filter chain
        filterChain.doFilter(request, response);
    }
}
