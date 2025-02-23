package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.PaymentResponse;
import com.palu_gada_be.palu_gada_be.model.Payment;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;

public class PaymentMapper {
    public static PaymentResponse toPaymentResponse(Payment payment){
        return PaymentResponse.builder()
                .id(payment.getId())
                .user(UserMapper.toUserResponse(payment.getUser()))
                .transferDate(DateTimeUtil.convertLocalDateTimeToString(payment.getTransferDate(), "dd-MM-yyyy HH:mm:ss"))
                .bank(payment.getBank())
                .amount(payment.getAmount())
                .vaNumber(payment.getVaNumber())
                .paymentType(payment.getPaymentType())
                .expiryTime(payment.getExpiryTime())
                .status(payment.getPaymentStatus())
                .createdAt(DateTimeUtil.convertLocalDateTimeToString(payment.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .updatedAt(DateTimeUtil.convertLocalDateTimeToString(payment.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .build();
    }
}
