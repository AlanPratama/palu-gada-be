package com.palu_gada_be.palu_gada_be.dto.response.auth;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterResponse {
    private String id;
    private String username;
    private String email;
}
