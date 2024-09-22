package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.PayoutResponse;
import com.palu_gada_be.palu_gada_be.model.Payout;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;

public class PayoutMapper {
    public static PayoutResponse toPaymentResponse(Payout payout){
        return PayoutResponse.builder()
                .id(payout.getId())
                .user(UserMapper.toUserResponse(payout.getUser()))
                .amount(payout.getAmount())
                .payoutType(payout.getPayoutType())
                .destinationNumber(payout.getDestinationNumber())
                .payoutStatus(payout.getPayoutStatus())
                .createdAt(DateTimeUtil.convertLocalDateTimeToString(payout.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .updatedAt(DateTimeUtil.convertLocalDateTimeToString(payout.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .build();
    }
}
