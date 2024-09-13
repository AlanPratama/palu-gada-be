package com.palu_gada_be.palu_gada_be.dto.response;

import lombok.*;

import java.util.Collection;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private Collection authorities;
}
