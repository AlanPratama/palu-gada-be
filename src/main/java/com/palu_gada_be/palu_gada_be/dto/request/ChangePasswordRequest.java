package com.palu_gada_be.palu_gada_be.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotNull(message = "Password lama tidak boleh kosong.")
    @Size(min = 8, max = 100, message = "Password lama harus memiliki antara 8 hingga 100 karakter.")
    private String oldPassword;

    @NotNull(message = "Password baru tidak boleh kosong.")
    @Size(min = 8, max = 100, message = "Password baru harus memiliki antara 8 hingga 100 karakter.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password baru harus mengandung setidaknya satu huruf besar, satu huruf kecil, satu angka, dan satu karakter spesial.")
    private String password;

    @NotNull(message = "Konfirmasi password tidak boleh kosong.")
    @Size(min = 8, max = 100, message = "Konfirmasi password harus memiliki antara 8 hingga 100 karakter.")
    private String passwordConfirm;
}
