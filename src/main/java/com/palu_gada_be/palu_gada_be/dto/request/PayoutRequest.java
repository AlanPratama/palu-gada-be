package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayoutRequest {

    @NotNull(message = "Amount Tidak boleh kosong")
    @Positive(message = "Amount harus lebih dari 0")
    private Long amount;

    @NotBlank(message = "Payout type tidak boleh kosong")
    private String payoutType;

    @NotBlank(message = "Destination number tidak boleh kosong")
    private String destinationNumber;

    @NotBlank(message = "Payout status tidak boleh kosong")
    private String payoutStatus;
}
