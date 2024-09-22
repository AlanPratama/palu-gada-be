package com.palu_gada_be.palu_gada_be.constant;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    ORDERED,
    PENDING,
    SETTLEMENT,
    CANCEL,
    DENY,
    EXPIRE,
    FAILURE
}
