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
    private static final String ADMIN_EXPECTED_SECRET_KEY = "zul bhayangkara alan birahi tomy semut hadiat hepatitis";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String adminKey = request.getHeader("X-Admin-Secret-Key");

        // Cek untuk endpoint yang memerlukan Admin Secret Key
        if (path.startsWith("/api/admin/users/")) {
            if (adminKey == null || !adminKey.equals(ADMIN_EXPECTED_SECRET_KEY)) {
                response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid Admin Secret Key");
                return;
            }

            // Cek otorisasi untuk memastikan pengguna memiliki peran SUPER_ADMIN
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_SUPER_ADMIN"))) {
                response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied");
                return;
            }
        }

        // Lanjutkan filter chain
        filterChain.doFilter(request, response);
    }
}
