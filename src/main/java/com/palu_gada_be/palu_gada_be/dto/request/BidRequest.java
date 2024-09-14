package com.palu_gada_be.palu_gada_be.dto.request;

import com.palu_gada_be.palu_gada_be.constant.BidStatus;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidRequest {
    private Long postId;
    private Long amount;
    private String message;
}
