package com.palu_gada_be.palu_gada_be.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayoutRequest {
    private Long amount;
    private String payoutType;
    private String destinationNumber;
    private String payoutStatus;
}
