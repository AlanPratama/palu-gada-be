package com.palu_gada_be.palu_gada_be.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomExpiryRequest {
    private String expiry_duration;
    private String unit;
}
