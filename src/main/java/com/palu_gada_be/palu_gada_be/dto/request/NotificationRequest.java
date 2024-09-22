package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    @NotNull(message = "ID pengguna tidak boleh kosong.")
    private Long userId;

    @NotNull(message = "Judul tidak boleh kosong.")
    @Size(min = 5, max = 100, message = "Judul harus memiliki antara 5 hingga 100 karakter.")
    private String title;

    @NotNull(message = "Deskripsi tidak boleh kosong.")
    @Size(min = 10, max = 500, message = "Deskripsi harus memiliki antara 10 hingga 500 karakter.")
    private String description;

    @NotNull(message = "Status dibaca tidak boleh kosong.")
    private Boolean isRead;

    @Pattern(regexp = "^(https?|ftp)://.*$", message = "Ikon harus berupa URL yang valid.")
    private String icon;
}

