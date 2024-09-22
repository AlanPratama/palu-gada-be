package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.AuthenticationRequest;
import com.palu_gada_be.palu_gada_be.dto.request.RefreshTokenRequest;
import com.palu_gada_be.palu_gada_be.dto.request.RegisterRequest;
import com.palu_gada_be.palu_gada_be.dto.response.AuthenticationResponse;
import com.palu_gada_be.palu_gada_be.dto.response.RefreshTokenResponse;
import com.palu_gada_be.palu_gada_be.dto.response.UserRegisterResponse;

public interface AuthService {
    UserRegisterResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshToken) throws Exception;
}
