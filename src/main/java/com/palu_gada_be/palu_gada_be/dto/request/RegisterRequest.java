package com.palu_gada_be.palu_gada_be.dto.request;

import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username tidak boleh kosong")
    @Size(min = 3, max = 50, message = "Username harus memiliki panjang antara 3 hingga 50 karakter")
    private String username;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 8, message = "Password harus memiliki minimal 8 karakter")
    private String password;
}
