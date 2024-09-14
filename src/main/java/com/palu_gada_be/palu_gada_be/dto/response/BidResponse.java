package com.palu_gada_be.palu_gada_be.dto.response;

import com.palu_gada_be.palu_gada_be.constant.BidStatus;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidResponse {
    private Long id;
    private UserResponse user;
    private PostResponse post;
    private Long amount;
    private String message;
    private BidStatus status;
}
