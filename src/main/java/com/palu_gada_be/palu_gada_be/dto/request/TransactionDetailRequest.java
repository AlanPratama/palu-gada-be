package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailRequest {
    @NotBlank(message = "Order id tidak boleh kosong")
    private String order_id;

    @Min(value = 1, message = "Amount tidak boleh kurang dari nol")
    private Long gross_amount;
}
