package com.palu_gada_be.palu_gada_be.dto.request;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    private String oldPassword;
    private String password;
    private String passwordConfirm;
}
