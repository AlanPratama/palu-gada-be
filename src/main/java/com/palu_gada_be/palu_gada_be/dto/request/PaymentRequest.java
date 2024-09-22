package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @NotNull(message = "Nama bank tidak boleh kosong.")
    @Size(min = 2, max = 50, message = "Nama bank harus antara 2 hingga 50 karakter.")
    private String bank;

    @NotNull(message = "Jumlah pembayaran tidak boleh kosong.")
    @Min(value = 1, message = "Jumlah pembayaran harus lebih dari 0.")
    private Long amount;

    @NotNull(message = "Tipe pembayaran tidak boleh kosong.")
    private String paymentType;
}

