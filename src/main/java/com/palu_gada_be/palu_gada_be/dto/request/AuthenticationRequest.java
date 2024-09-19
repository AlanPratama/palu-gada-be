package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "Username/Email tidak boleh kosong")
    private String usernameOrEmail;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;
}
