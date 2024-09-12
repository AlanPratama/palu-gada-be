package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.dto.request.auth.AuthenticationRequest;
import com.palu_gada_be.palu_gada_be.dto.request.auth.RefreshTokenRequest;
import com.palu_gada_be.palu_gada_be.dto.request.auth.RegisterRequest;
import com.palu_gada_be.palu_gada_be.dto.response.auth.AuthenticationResponse;
import com.palu_gada_be.palu_gada_be.dto.response.auth.RefreshTokenResponse;
import com.palu_gada_be.palu_gada_be.dto.response.auth.UserRegisterResponse;
import com.palu_gada_be.palu_gada_be.model.Role;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.repository.RoleRepository;
import com.palu_gada_be.palu_gada_be.repository.UserRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserRegisterResponse register(RegisterRequest request) {
        if (userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exist");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                   Role role = new Role();
                   role.setName("ROLE_USER");
                   return roleRepository.save(role);
                });

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(new HashSet<>(Set.of(userRole)))
                .build();

        User createdNewUser = userRepository.save(user);

        return UserRegisterResponse.builder()
                .id(String.valueOf(createdNewUser.getId()))
                .email(createdNewUser.getEmail())
                .username(createdNewUser.getUsername())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
        );

        User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshToken) throws Exception {
        if (!jwtService.validateRefreshToken(refreshToken.getRefreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtService.extractUsername(refreshToken.getRefreshToken());

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtService.generateToken(user);

        return RefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }
}
