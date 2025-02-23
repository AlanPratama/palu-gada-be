package com.palu_gada_be.palu_gada_be.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MidtransResponse {
    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("gross_amount")
    private String grossAmount;

    private String currency;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("transaction_time")
    private String transactionTime;

    @JsonProperty("transaction_status")
    private String transactionStatus;

    @JsonProperty("fraud_status")
    private String fraudStatus;

    @JsonProperty("va_numbers")
    private List<VaNumber> vaNumbers;

    @JsonProperty("expiry_time")
    private String expiryTime;

    @Setter
    @Getter
    public static class VaNumber {
        @JsonProperty("bank")
        private String bank;
        @JsonProperty("va_number")
        private String vaNumber;
    }
}
