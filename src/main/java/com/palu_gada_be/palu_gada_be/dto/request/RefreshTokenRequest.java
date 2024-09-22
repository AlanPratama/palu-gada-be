package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "Refresh token tidak boleh kosong")
    private String refreshToken;
}
