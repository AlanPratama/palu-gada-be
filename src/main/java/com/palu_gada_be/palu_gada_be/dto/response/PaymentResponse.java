package com.palu_gada_be.palu_gada_be.dto.response;

import com.palu_gada_be.palu_gada_be.constant.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long id;
    private UserResponse user;
    private String transferDate;
    private String bank;
    private Long amount;
    private Long fee;
    private String vaNumber;
    private String paymentType;
    private String expiryTime;
    private PaymentStatus status;
    private String createdAt;
    private String updatedAt;
}
