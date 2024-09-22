package com.palu_gada_be.palu_gada_be.dto.request;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 8, message = "Password harus memiliki minimal 8 karakter")
    private String password;

    @NotBlank(message = "Konfirmasi password tidak boleh kosong")
    @Size(min = 8, message = "Konfirmasi password harus memiliki minimal 8 karakter")
    private String passwordConfirm;
}
