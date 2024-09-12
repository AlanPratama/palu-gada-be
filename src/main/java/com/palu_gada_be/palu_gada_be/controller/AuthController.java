package com.palu_gada_be.palu_gada_be.controller;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.auth.AuthenticationRequest;
import com.palu_gada_be.palu_gada_be.dto.request.auth.RefreshTokenRequest;
import com.palu_gada_be.palu_gada_be.dto.request.auth.RegisterRequest;
import com.palu_gada_be.palu_gada_be.dto.response.auth.AuthenticationResponse;
import com.palu_gada_be.palu_gada_be.dto.response.auth.RefreshTokenResponse;
import com.palu_gada_be.palu_gada_be.dto.response.auth.UserRegisterResponse;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.service.AuthService;
import com.palu_gada_be.palu_gada_be.service.UserService;
import com.palu_gada_be.palu_gada_be.util.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConstantEndpoint.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public AuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/register")
    public UserRegisterResponse register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return authService.register(request);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(
        @RequestBody @Valid RegisterRequest request
    ) {
        return Response.renderJSON(userService.createAdmin(request), "Admin Succesfully Created", HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshToken) throws Exception {
        return authService.refreshToken(refreshToken);
    }
}
