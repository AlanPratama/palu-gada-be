package com.palu_gada_be.palu_gada_be.dto.request.auth;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
