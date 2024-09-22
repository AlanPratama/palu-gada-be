package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidRequest {

    @NotNull(message = "ID postingan tidak boleh kosong.")
    private Long postId;

    @NotNull(message = "Jumlah tawaran tidak boleh kosong.")
    @Min(value = 1, message = "Jumlah tawaran harus lebih dari 0.")
    private Long amount;

    @NotNull(message = "Pesan tidak boleh kosong.")
    @Size(min = 10, max = 500, message = "Pesan harus memiliki antara 10 hingga 500 karakter.")
    private String message;
}
