package com.palu_gada_be.palu_gada_be.dto.response;

import com.palu_gada_be.palu_gada_be.constant.PayoutStatus;
import com.palu_gada_be.palu_gada_be.constant.PayoutType;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayoutResponse {
    private Long id;
    private UserResponse user;
    private Long amount;
    private PayoutType payoutType;
    private String destinationNumber;
    private PayoutStatus payoutStatus;
    private String createdAt;
    private String updatedAt;
}
