package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.auth.AuthenticationRequest;
import com.palu_gada_be.palu_gada_be.dto.request.auth.RefreshTokenRequest;
import com.palu_gada_be.palu_gada_be.dto.request.auth.RegisterRequest;
import com.palu_gada_be.palu_gada_be.dto.response.auth.AuthenticationResponse;
import com.palu_gada_be.palu_gada_be.dto.response.auth.RefreshTokenResponse;
import com.palu_gada_be.palu_gada_be.dto.response.auth.UserRegisterResponse;

public interface AuthService {
    UserRegisterResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshToken) throws Exception;
}
