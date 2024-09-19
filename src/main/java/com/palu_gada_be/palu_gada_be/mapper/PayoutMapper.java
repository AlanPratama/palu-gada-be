package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.PayoutResponse;
import com.palu_gada_be.palu_gada_be.model.Payout;

public class PayoutMapper {
    public static PayoutResponse toPaymentResponse(Payout payout){
        return PayoutResponse.builder()
                .id(payout.getId())
                .user(UserMapper.toUserResponse(payout.getUser()))
                .amount(payout.getAmount())
                .payoutType(payout.getPayoutType())
                .destinationNumber(payout.getDestinationNumber())
                .payoutStatus(payout.getPayoutStatus())
                .build();
    }
}
