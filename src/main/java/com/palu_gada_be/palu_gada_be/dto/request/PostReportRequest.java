package com.palu_gada_be.palu_gada_be.dto.request;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostReportRequest {

    @NotNull(message = "ID postingan tidak boleh kosong.")
    private Long postId;

    @NotNull(message = "Pesan tidak boleh kosong.")
    @Size(min = 10, message = "Pesan harus memiliki minimal 10 karakter.")
    private String message;
}

