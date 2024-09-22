package com.palu_gada_be.palu_gada_be.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MidtransRequest {
    private String payment_type;
    private TransactionDetailRequest transaction_details;
    private BankTransferRequest bank_transfer;
    private CustomExpiryRequest custom_expiry;
}
