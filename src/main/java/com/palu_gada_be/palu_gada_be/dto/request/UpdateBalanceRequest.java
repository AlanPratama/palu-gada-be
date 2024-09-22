package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBalanceRequest {

    @NotNull(message = "Jumlah tidak boleh kosong")
    @Min(value = 1, message = "Jumlah harus lebih besar atau sama dengan 1")
    private Long amount;

}
