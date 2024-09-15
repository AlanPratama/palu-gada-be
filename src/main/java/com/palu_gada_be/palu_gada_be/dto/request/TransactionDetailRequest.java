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
    @NotBlank(message = "Order id is required")
    private String order_id;

    @Min(value = 1, message = "Product id must be at least 1")
    private Long gross_amount;
}
