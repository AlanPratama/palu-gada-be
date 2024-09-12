package com.palu_gada_be.palu_gada_be.dto.request.user;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    private String password;
    private String passwordConfirm;
}
